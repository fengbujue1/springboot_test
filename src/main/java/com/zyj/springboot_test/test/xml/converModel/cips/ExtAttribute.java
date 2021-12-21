package com.zyj.springboot_test.test.xml.converModel.cips;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


/**
 * <p>Title: ��չ���Ա��� </p>
 *
 * <p>Description: </p>
 *
 * <p>Company: �����ź��ǿƼ��ɷ����޹�˾</p>
 *
 * @author jiangzongbing
 * 
 * @since��2015-3-5 ����06:15:29
 * 
 */
public class ExtAttribute implements Serializable{
	private static final long serialVersionUID = -1637775670224713998L;

	/**
	 * �����ֶ�1
	 */
	private String reserved1;

	/**
	 * �����ֶ�2
	 */
	private String reserved2;

	/**
	 * �����ֶ�3
	 */
	private String reserved3;

	/**
	 * �����ֶ�4
	 */
	private String reserved4;

	/**
	 * �����ֶ�5
	 */
	private String reserved5;
	
	/**
	 * Ԥ����չ����
	 */
	private Map attributes;

	/**
	 * 
	 * @Description:�����ֶ�
	 * @return
	 * @author jiangzongbing
	 * @since��2015-3-5 ����06:21:45
	 */
	public String getReserved1() {
		return reserved1;
	}

	/**
	 * 
	 * @Description:�����ֶ�
	 * @param reserved1
	 * @author jiangzongbing
	 * @since��2015-3-5 ����06:22:02
	 */
	public void setReserved1(String reserved1) {
		this.reserved1 = reserved1;
	}

	/**
	 * 
	 * @Description:�����ֶ�2
	 * @return
	 * @author jiangzongbing
	 * @since��2015-3-5 ����06:21:45
	 */
	public String getReserved2() {
		return reserved2;
	}

	/**
	 * 
	 * @Description:�����ֶ�2
	 * @param reserved2
	 * @author jiangzongbing
	 * @since��2015-3-5 ����06:22:02
	 */
	public void setReserved2(String reserved2) {
		this.reserved2 = reserved2;
	}

	/**
	 * 
	 * @Description:�����ֶ�3
	 * @return
	 * @author jiangzongbing
	 * @since��2015-3-5 ����06:21:45
	 */
	public String getReserved3() {
		return reserved3;
	}

	/**
	 * 
	 * @Description:�����ֶ�3
	 * @param reserved3
	 * @author jiangzongbing
	 * @since��2015-3-5 ����06:22:02
	 */
	public void setReserved3(String reserved3) {
		this.reserved3 = reserved3;
	}

	/**
	 * 
	 * @Description:�����ֶ�4
	 * @return
	 * @author jiangzongbing
	 * @since��2015-3-5 ����06:21:45
	 */
	public String getReserved4() {
		return reserved4;
	}

	/**
	 * 
	 * @Description:�����ֶ�4
	 * @param reserved4
	 * @author jiangzongbing
	 * @since��2015-3-5 ����06:22:02
	 */
	public void setReserved4(String reserved4) {
		this.reserved4 = reserved4;
	}

	/**
	 * 
	 * @Description:�����ֶ�5
	 * @return
	 * @author jiangzongbing
	 * @since��2015-3-5 ����06:21:45
	 */
	public String getReserved5() {
		return reserved5;
	}

	/**
	 * 
	 * @Description:�����ֶ�5
	 * @param reserved5
	 * @author jiangzongbing
	 * @since��2015-3-5 ����06:22:02
	 */
	public void setReserved5(String reserved5) {
		this.reserved5 = reserved5;
	}
	/**
	 * 
	 * @Description:Ԥ����չ����
	 * @return
	 * @author wangyukun
	 * @since��2015-10-25 ����06:22:02
	 */
	public Map getAttributes() {
		if (attributes == null) {
			attributes = new HashMap();
		}
		return attributes;
	}
	
	public Object getAttribute(String key) {
		if (attributes == null) {
			attributes = new HashMap();
		}
		return attributes.get(key);
	}
	
	public Object getAttribute(String key, Object defaultValue) {
		Object o = getAttribute(key);
		return o == null ? defaultValue : o;
	}
	/**
	 * 
	 * @Description:Ԥ����չ����
	 * @param attributes
	 * @author wangyukun
	 * @since��2015-10-25 ����06:22:02
	 */
	public void setAttributes(Map attributes) {
		if (attributes == null) {
			attributes = new HashMap();
		}
		this.attributes = attributes;
	}
	public void setAttribute(String key, Object value) {
		if (attributes == null) {
			attributes = new HashMap();
		}
		attributes.remove(key);
		attributes.put(key, value);
	}

	
	
}
