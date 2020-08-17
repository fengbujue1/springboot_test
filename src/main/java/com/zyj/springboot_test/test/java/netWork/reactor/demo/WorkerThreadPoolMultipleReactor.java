package com.zyj.springboot_test.test.java.netWork.reactor.demo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * 工作线程池版的Reactor模式。<br/>
 * 客户端连接请求有限，更多的是连接后的读写请求，特别是业务处理耗时不可控，
 * 可以用线程池来改造一下Handler的业务处理，从而提高处理效率。
 */
public class WorkerThreadPoolMultipleReactor {

	// 增加一个线程池
	static ExecutorService mainThreadPool = CustomThreadPool.buildPool();
	public static void main(String[] args) {
		WorkerThreadPoolMultipleReactor reactor = new WorkerThreadPoolMultipleReactor();
		reactor.init(8088);
	}
	
	final int subReaLen = 8;
	SubReactor[] subReaGroup = new SubReactor[subReaLen];
	public void init(int port) {
		for(int i = 0; i < subReaGroup.length; i++) {
			try {
				subReaGroup[i] = new SubReactor();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		try {
			MainReactor main = new MainReactor();
			ServerSocketChannel serverSocket = ServerSocketChannel.open();
			serverSocket.bind(new InetSocketAddress(port));
			main.register(serverSocket);
			mainThreadPool.submit(main);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 处理多个客户端并发交付服务请求的实现类。即Reactor模式。<br>
	 * 请求抵达后，服务处理程序使用I/O多路复用策略，然后同步地派发这些请求至相关的请求处理程序。
	 * MainReactor只处理Accept IO事件
	 */
	class MainReactor implements Runnable {
		final Selector sel;
		ServerSocketChannel serSocket;
		
		public MainReactor() throws IOException {
			sel = Selector.open();
		}
		
		public void register(ServerSocketChannel serverSocket) {
			try {
				serSocket = serverSocket;
				serSocket.configureBlocking(false);
				// 获取serverSocket在Selector中注册的标识sk
				// sk绑定了serverSocket与Selector的关系，维护channel事件
				SelectionKey sk = serSocket.register(sel, SelectionKey.OP_ACCEPT);
				//serverSocket.bind(new InetSocketAddress(port));
				// 附加一个Runable的Accept事件处理者
				sk.attach(new Acceptor(sel, serSocket));
			} catch (ClosedChannelException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		/**
		 * 充分利用Selector达到IO多路复用的效果。
		 */
		@Override
		public void run() {
			while(! Thread.interrupted()) {
				try {
					sel.select();
					Set<SelectionKey> selKeySet = sel.selectedKeys();
					Iterator<SelectionKey> it = selKeySet.iterator();
					while (it.hasNext()) {
						SelectionKey key = it.next();
						System.out.println(key.readyOps());
						dispatch(key);	// 具体执行通过dispatch方法，派发执行
					}
					selKeySet.clear();	// 清除已经处理过的事件
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		private void dispatch(SelectionKey selectionKey) {
			// 从attachment中获得IO事件执行对象
			Runnable r = (Runnable) selectionKey.attachment();
			if(r != null) { r.run(); }	// 执行具体事件的处理
		}
	}
	
	/**
	 * 将服务端accept处理，拆分到Accepter类中。
	 *
	 */

	class Acceptor implements Runnable {
		final ServerSocketChannel serverSocket;
		final Selector sel;
		// 使用了这样SubReactor数组，意味着有多个Selector处理读写事件
		final SubReactor[] subRea;
		final AtomicInteger seq = new AtomicInteger();
		
		public Acceptor(Selector selector, ServerSocketChannel serChann) {
			serverSocket = serChann;
			subRea = subReaGroup;
			sel = selector;
		}
		
		/**
		 * 由Reactor的dispatch调用，处理客户端连接请求accept事件。
		 */
		@Override
		public void run() {
			try {
				SocketChannel c = serverSocket.accept();
				if(c != null) {
					// 注册客户端通道的Read事件处理者
					//new Handler(sel, c);
					
					// 不再是直接绑定Handler，而是绑定到某个SubReactor上去。
					int index = seq.getAndIncrement();
					int idx = index % subRea.length;	// 采用取模的方式
					subRea[idx].register(c);
					mainThreadPool.execute(subRea[idx]);
					
					// 避免seq溢出
					if(seq.get() == Integer.MAX_VALUE >> 1) {
						seq.set(0);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 用来处理IO读写事件的Reactor
	 *
	 */
	class SubReactor implements Runnable {
		final Selector sel;
		SocketChannel c;
		public SubReactor() throws IOException {
			sel = Selector.open();
		}
		
		public void register(SocketChannel clientChann) {
			c = clientChann;
			try {
				c.configureBlocking(false);
				SelectionKey k = c.register(sel, SelectionKey.OP_READ);
				new Handler(k);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		/**
		 * 充分利用Selector达到IO多路复用的效果。
		 */
		@Override
		public void run() {
			while(! Thread.interrupted()) {
				try {
					sel.select();
					Set<SelectionKey> selKeySet = sel.selectedKeys();
					Iterator<SelectionKey> it = selKeySet.iterator();
					while (it.hasNext()) {
						SelectionKey key = it.next();
						System.out.println(key.readyOps());
						dispatch(key);	// 具体执行通过dispatch方法，派发执行
					}
					selKeySet.clear();	// 清除已经处理过的事件
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}


		private void dispatch(SelectionKey selectionKey) {
			// 从attachment中获得IO事件执行对象
			Runnable r = (Runnable) selectionKey.attachment();
			if(r != null) { r.run(); }	// 执行具体事件的处理
		}
	}
	
	/**
	 * 客户端读写事件处理器。<br/>
	 * 将客户端读写操作处理，拆分到Hadler类中。
	 */
	class Handler implements Runnable{
		final SocketChannel socket;	// 客户端通道
		final SelectionKey sk;		// 
		final int MAXIN = 1024;
		ByteBuffer input = ByteBuffer.allocate(MAXIN);
		// 增加一个线程池
		ExecutorService threadPool = CustomThreadPool.buildPool();
		
		/**
		 * 通过SelectionKey构建对象
		 * @param key
		 */
		public Handler(SelectionKey key) {
			// 获得客户端通道与Selector的绑定Key
			sk = key;
			socket = (SocketChannel) key.channel();
			sk.attach(this);
			//sel.wakeup();	// 激活在select()方法上的线程，告诉它感兴趣的事件发生变化了
		}
		
		/**
		 * 完成客户端通道的Selector注册，获得相应的SelectionKey，并绑定处理者为自己。
		 * @param sel	选择器
		 * @param c		客户端连接通道
		 * @throws IOException
		 */
		public Handler(Selector sel, SocketChannel c) throws IOException {
			socket = c;
			socket.configureBlocking(false);
			// 获得客户端通道与Selector的绑定Key
			sk = socket.register(sel, 0);
			sk.interestOps(SelectionKey.OP_READ);	// 读事件感兴趣
			sk.attach(this);	// 绑定自己为读事件的处理器
			//sel.wakeup();	// 激活在select()方法上的线程，告诉它感兴趣的事件发生变化了
		}
		
		/**
		 * 响应客户端通道上的读写事件，进行处理。
		 */
		@Override
		public void run() {
			try {
				if(sk.isReadable()) {
					read();
				}
			} catch (IOException ex) { 
				ex.printStackTrace();
			}
		}
		
		void read() throws IOException {
			socket.read(input);
			if (inputIsComplete()) {
				// 使用线程池进行业务处理
				input.flip();
				threadPool.execute(new Processer(sk, input));
				input.clear();
			}
		}
		
		boolean inputIsComplete() {
			return input.position() > 0;
		}
		// 业务处理交给Processer处理器
		//void process() {}
	}
	
	class Processer implements Runnable {
		final ByteBuffer input;
		final SelectionKey sk;
		public Processer(SelectionKey selKey, ByteBuffer in) {
			input = ByteBuffer.wrap(in.array(), 0, in.limit());
			sk = selKey;
		}
		
		@Override
		public void run() {
			// 模拟业务逻辑处理
			System.out.println(new String(input.array(), input.position(), input.limit()));
			//LockSupport.parkNanos(TimeUnit.MILLISECONDS.toNanos(300));
			String msg = "Now time is: " + (new Date()).toString();
			ByteBuffer output = ByteBuffer.allocate(1024);
			output.put(msg.getBytes());
			try {
				output.flip();
				new Sender(sk, output).run();
			} catch (ClosedChannelException e) {
				e.printStackTrace();
			}
		}
	}
	
	class Sender implements Runnable {
		final SocketChannel socket;
		ByteBuffer output = null;
		SelectionKey sk;
		final Object oldAttachment;
		public Sender(SelectionKey selKey, ByteBuffer respMsg) throws ClosedChannelException {
			socket = (SocketChannel) selKey.channel();
			output = ByteBuffer.wrap(respMsg.array(), 0, respMsg.limit());
			sk = selKey;
			// 注册感兴趣写事件，进行响应内容输出
			sk.interestOps(sk.interestOps() | SelectionKey.OP_WRITE);
			oldAttachment = sk.attach(this);
			sk.selector().wakeup();
		}

		@Override
		public void run() {
			try {
				if(sk.isWritable()) {
					socket.write(output);
					if(outputIsComplete()) {
						output.clear();
						sk.attach(oldAttachment);
						// 只有写事件操作完成了，才取消
						// 思考为什么没有用循环来处理？
						sk.interestOps(sk.interestOps() & (~ SelectionKey.OP_WRITE));

					}
					sk.cancel();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		boolean outputIsComplete() {
			// output.hasRemaining() true表示还有数据要发送，也就是发送未完成
			// 这个方法表示是否完成了输出，true表示完成了，所以取反。
			return !output.hasRemaining();
		}
	}
}
