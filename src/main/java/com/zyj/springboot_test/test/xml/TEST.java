package com.zyj.springboot_test.test.xml;

import com.zyj.springboot_test.util.XMLUtil;
import org.apache.commons.lang.StringUtils;
import org.jdom.Attribute;
import org.jdom.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * 参考文档归档于：http://192.168.0.2/svn/doc/Doc.Dev/BP/开发备忘/Wiki/交易报文规范/TX0022-跨境人民币支付（CIPS）.xlsx
 *
 * @author 周赟吉
 * @since 2021/12/7
 */
public class TEST {
    public static boolean tag = true;
    public static void main(String[] args) throws Exception {

        String s = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<Document xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns=\"urn:iso:std:iso:20022:tech:xsd:pacs.008.001.10\">\n" +
                "  <FIToFICstmrCdtTrf>\n" +
                "    <GrpHdr>\n" +
                "      <MsgId>CMEPCI012021073010394800022</MsgId>\n" +
                "      <CreDtTm>2021-07-30T11:31:58</CreDtTm>\n" +
                "      <NbOfTxs>1</NbOfTxs>\n" +
                "      <SttlmInf>\n" +
                "        <SttlmMtd>CLRG</SttlmMtd>\n" +
                "      </SttlmInf>\n" +
                "    </GrpHdr>\n" +
                "    <CdtTrfTxInf>\n" +
                "      <PmtId>\n" +
                "        <EndToEndId>CMEPCI012021073011315800024</EndToEndId>\n" +
                "        <TxId>CMEPCI012021073010394800022</TxId>\n" +
                "      </PmtId>\n" +
                "      <PmtTpInf>\n" +
                "        <CtgyPurp>\n" +
                "          <Prtry>OTFX</Prtry>\n" +
                "        </CtgyPurp>\n" +
                "      </PmtTpInf>\n" +
                "      <IntrBkSttlmAmt Ccy=\"CNY\">50.00</IntrBkSttlmAmt>\n" +
                "      <IntrBkSttlmDt>2021-07-30</IntrBkSttlmDt>\n" +
                "      <SttlmPrty>URGT</SttlmPrty>\n" +
                "      <ChrgBr>DEBT</ChrgBr>\n" +
                "      <InstgAgt>\n" +
                "        <FinInstnId>\n" +
                "          <ClrSysMmbId>\n" +
                "            <MmbId>COMMCNSHXXX</MmbId>\n" +
                "          </ClrSysMmbId>\n" +
                "        </FinInstnId>\n" +
                "      </InstgAgt>\n" +
                "      <InstdAgt>\n" +
                "        <FinInstnId>\n" +
                "          <ClrSysMmbId>\n" +
                "            <MmbId>COMMKRSE888</MmbId>\n" +
                "          </ClrSysMmbId>\n" +
                "        </FinInstnId>\n" +
                "      </InstdAgt>\n" +
                "      <Dbtr>\n" +
                "        <Nm>五矿集团财务有限责任公司</Nm>\n" +
                "        <PstlAdr>\n" +
                "          <AdrLine>5 sanlihelu </AdrLine>\n" +
                "        </PstlAdr>\n" +
                "        <Id>\n" +
                "          <OrgId>\n" +
                "            <Othr>\n" +
                "              <Id>CN002007932</Id>\n" +
                "              <SchmeNm>\n" +
                "                <Cd>CIPS</Cd>\n" +
                "              </SchmeNm>\n" +
                "            </Othr>\n" +
                "          </OrgId>\n" +
                "        </Id>\n" +
                "        <CtryOfRes>CN</CtryOfRes>\n" +
                "      </Dbtr>\n" +
                "      <DbtrAcct>\n" +
                "        <Id>\n" +
                "          <Othr>\n" +
                "            <Id>372005501013000003968</Id>\n" +
                "          </Othr>\n" +
                "        </Id>\n" +
                "      </DbtrAcct>\n" +
                "      <DbtrAgt>\n" +
                "        <FinInstnId>\n" +
                "          <ClrSysMmbId>\n" +
                "            <MmbId>301100000023</MmbId>\n" +
                "          </ClrSysMmbId>\n" +
                "          <Nm>交通银行股份有限公司北京市分行营业部</Nm>\n" +
                "          <PstlAdr>\n" +
                "            <AdrLine>33 jinrongjie</AdrLine>\n" +
                "          </PstlAdr>\n" +
                "          <Othr>\n" +
                "            <Id>CN002007932</Id>\n" +
                "          </Othr>\n" +
                "        </FinInstnId>\n" +
                "      </DbtrAgt>\n" +
                "      <DbtrAgtAcct>\n" +
                "        <Id>\n" +
                "          <Othr>\n" +
                "            <Id>372005501013000003968</Id>\n" +
                "          </Othr>\n" +
                "        </Id>\n" +
                "      </DbtrAgtAcct>\n" +
                "      <CdtrAgt>\n" +
                "        <FinInstnId>\n" +
                "          <ClrSysMmbId>\n" +
                "            <MmbId>COMMKRSE888</MmbId>\n" +
                "          </ClrSysMmbId>\n" +
                "          <Nm>T</Nm>\n" +
                "        </FinInstnId>\n" +
                "      </CdtrAgt>\n" +
                "      <Cdtr>\n" +
                "        <Nm>E</Nm>\n" +
                "        <PstlAdr>\n" +
                "          <AdrLine>R</AdrLine>\n" +
                "        </PstlAdr>\n" +
                "        <CtryOfRes>AU</CtryOfRes>\n" +
                "      </Cdtr>\n" +
                "      <CdtrAcct>\n" +
                "        <Id>\n" +
                "          <Othr>\n" +
                "            <Id>22222</Id>\n" +
                "          </Othr>\n" +
                "        </Id>\n" +
                "      </CdtrAcct>\n" +
                "      <SplmtryData>\n" +
                "        <PlcAndNm>/Document/FIToFICstmrCdtTrf/CdtTrfTxInf</PlcAndNm>\n" +
                "        <Envlp>\n" +
                "          <Cnts>\n" +
                "            <XpctdSttlmDt>2021-07-30</XpctdSttlmDt>\n" +
                "          </Cnts>\n" +
                "        </Envlp>\n" +
                "      </SplmtryData>\n" +
                "    </CdtTrfTxInf>\n" +
                "  </FIToFICstmrCdtTrf>\n" +
                "</Document>";
        Element document = XMLUtil.getRootElement(s);
        ArrayList<String> contents = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
        getChildrenTest(document, contents);

        for (String content : contents) {
            stringBuilder.append(content + "|");
        }
        System.out.println(stringBuilder);
    }

    private static void getChildrenTest(Element root,List<String> contents) {
        List<Element> children = root.getChildren();
        for (int i = 0; i < children.size(); i++) {
            getChildrenTest(children.get(i),contents);
        }
        if (StringUtils.isNotBlank(root.getTextTrim())) {
            Attribute ccy = root.getAttribute("Ccy");
            if (ccy != null && StringUtils.isNotBlank(ccy.getValue())) {
                contents.add(ccy.getValue() + root.getTextTrim());
                return;
            }
            contents.add(root.getTextTrim());
        }
    }
}
