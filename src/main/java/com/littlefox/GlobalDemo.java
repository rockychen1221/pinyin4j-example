package com.littlefox;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by rockychen on 2018/5/5 0005 下午 15:36
 * Description:
 */
public class GlobalDemo {

  public static    Properties properties  = new Properties();

    public static void main(String[] args) throws Exception {

        // 使用ClassLoader加载properties配置文件生成对应的输入流
        InputStream in = GlobalDemo.class.getClassLoader().getResourceAsStream("E:\\WorkSpace\\pinyin4j-example\\src\\main\\java\\data.properties");
        // 使用properties对象加载输入流
        properties.load(in);
        //获取key对应的value值


        // TODO Auto-generated method stub

        // 1.创建解析器工厂
        DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
        // 2.通过解析器工厂创建解析器对象
        DocumentBuilder builder=factory.newDocumentBuilder();
        //3.通过解析器解析xml,在内存中构建dom树(中文)
        Document cn_dom=builder.parse(new File("global_city/LocList_cn.xml"));
        // 英文Dom
        Document en_dom=builder.parse(new File("global_city/LocList_en.xml"));
        //操作
        //得到xml文件的根元素
        Element cn_root=cn_dom.getDocumentElement();
        Element en_root=en_dom.getDocumentElement();

        //NodeList childList=root.getChildNodes();//获取当前元素下的所有子元 素

        //获取当前元素下指定的 子元素
        NodeList cn_childList=cn_root.getElementsByTagName("CountryRegion");
        NodeList en_childList=en_root.getElementsByTagName("CountryRegion");


       // System.out.println(cn_childList.getLength());
        //遍历所有的子节点
        int country=10000;
        for(int i=0;i<cn_childList.getLength();i++){


            Node cn_node=cn_childList.item(i);
            Element  cn_ele=(Element)cn_node;
            //如果是Element节点
            //	if(node.getNodeType()==Node.ELEMENT_NODE)
            for (int j = 0; j < en_childList.getLength(); j++) {

                Node en_node=en_childList.item(j);
                Element  en_ele=(Element)en_node;
                if (cn_ele.getAttribute("Code").equals(en_ele.getAttribute("Code"))){

                    int count=++country;

                    int sum=1*(i+1)+100;
                    System.out.println("1、国家 ('zh_CN',\""+cn_ele.getAttribute("Name")+"\",'AREA-"+sum+"');");
                    System.out.println("1、国家 ('en_US',\""+en_ele.getAttribute("Name")+"\",'AREA-"+sum+"');");


                  //  System.out.println("1、('"+sum+"','AREA."+sum+"','0');");

                   // System.out.println("1、国家名称CN："+cn_ele.getAttribute("Name")+"   国家名称EN: "+en_ele.getAttribute("Name")+"   国家Code="+cn_ele.getAttribute("Code"));
                    getStateChild(cn_ele,en_ele,sum);
                }
            }

               // index++;

                System.out.println();


        }
    }


  static   int total=0;
   static   int state=20000;
    public static void getStateChild(Element cn_ele,Element en_ele,int sum){
        NodeList cn_state= cn_ele.getElementsByTagName("State");
        NodeList en_state= en_ele.getElementsByTagName("State");
        for (int j = 0; j < cn_state.getLength(); j++) {
            Node cn_state_node=cn_state.item(j);
            Element  cn_state_ele=(Element)cn_state_node;
            for (int i = 0; i < en_state.getLength(); i++) {
                Node en_state_node=en_state.item(i);
                Element  en_state_ele=(Element)en_state_node;
                    if (cn_state_ele.getAttribute("Code").equals(en_state_ele.getAttribute("Code"))){
                        if (cn_state_ele.hasAttribute("Code")){
                            int count=state++;

                            System.out.println("===2、省份/州 ('zh_CN',\""+cn_state_ele.getAttribute("Name")+"\",'AREA-"+(1*(j+1)+sum*100)+"');");
                            System.out.println("===2、省份/州 ('en_US',\""+en_state_ele.getAttribute("Name")+"\",'AREA-"+(1*(j+1)+sum*100)+"');");

                          //  System.out.println("===2、 ('"+(1*(j+1)+sum*100)+"','AREA."+(1*(j+1)+sum*100)+"','1');");
                            getCitiChild(cn_state_ele,en_state_ele,(1*(j+1)+sum*100),"y");
                        }else{
                            total++;
                            getCitiChild(cn_state_ele,en_state_ele,(1*(j+1)+sum*100),"n");
                        }

                       // getCitiChild(cn_state_ele,en_state_ele,(1000*(j+1)+sum));
                       // System.out.println(1000*(j+1));


                        //  System.out.println("===2、省份名称CN："+cn_state_ele.getAttribute("Name")+"   省份名称EN: "+en_state_ele.getAttribute("Name")+"    省份Code="+en_state_ele.getAttribute("Code"));

                    }
            }
        }


       // System.out.println(cn_state.getLength()+"============"+total);
    }


    static   int city=30000;
    public static void getCitiChild(Element cn_ele,Element en_ele,int sum,String istype){
        NodeList cn_citi= cn_ele.getElementsByTagName("City");
        NodeList en_citi= en_ele.getElementsByTagName("City");
        for (int k = 0; k < cn_citi.getLength(); k++) {
            Node cn_citi_node=cn_citi.item(k);
            Element  cn_citi_ele=(Element)cn_citi_node;

            for (int l = 0; l < en_citi.getLength(); l++) {
                Node en_citi_node=en_citi.item(l);
                Element  en_citi_ele=(Element)en_citi_node;

                if (cn_citi_ele.getAttribute("Code").equals(en_citi_ele.getAttribute("Code"))){

                    int count=city++;

                    if ("y".equals(istype)){
                        System.out.println("======3、城市 ('zh_CN',\""+cn_citi_ele.getAttribute("Name")+"\",'AREA-"+(1*(k+1)+sum*100)+"');");
                       System.out.println("======3、城市 ('en_US',\""+en_citi_ele.getAttribute("Name")+"\",'AREA-"+(1*(k+1)+sum*100)+"');");

                    //   System.out.println("======3、('"+(1*(k+1)+sum*100)+"','AREA."+(1*(k+1)+sum*100)+"','2');");

                        getRegionChild(cn_citi_ele,en_citi_ele,(1*(k+1)+sum*100),"y");
                    }else {
                        System.out.println("======3、没有州的城市 ('zh_CN',\""+cn_citi_ele.getAttribute("Name")+"\",'AREA-"+(1*(k+1)+sum)+"');");
                        System.out.println("======3、没有州的城市 ('en_US',\""+en_citi_ele.getAttribute("Name")+"\",'AREA-"+(1*(k+1)+sum)+"');");

                       // System.out.println("======3、('"+(1*(k+1)+sum)+"','AREA."+(1*(k+1)+sum)+"','1');");

                        getRegionChild(cn_citi_ele,en_citi_ele,(1*(k+1)+sum),"n");
                    }

                    //  System.out.println("======3、城市名称CN："+cn_citi_ele.getAttribute("Name")+"   城市名称EN: "+en_citi_ele.getAttribute("Name")+"   城市Code="+en_citi_ele.getAttribute("Code"));
                   // getRegionChild(cn_citi_ele,en_citi_ele);
                }
            }
        }
    }



    static   int region=40000;

    public static void getRegionChild(Element cn_ele,Element en_ele,int sum,String istype){
        NodeList cn_region= cn_ele.getElementsByTagName("Region");
        NodeList en_region= en_ele.getElementsByTagName("Region");

        for (int m = 0; m < cn_region.getLength(); m++) {
            Node cn_region_node=cn_region.item(m);
            Element  cn_region_ele=(Element)cn_region_node;


            for (int n = 0; n < en_region.getLength(); n++) {
                Node en_region_node=en_region.item(n);
                Element  en_region_ele=(Element)en_region_node;

                if (cn_region_ele.getAttribute("Code").equals(en_region_ele.getAttribute("Code"))){

                    //   System.out.println("=========4、区县名称CN："+cn_region_ele.getAttribute("Name")+"   区县名称EN: "+en_region_ele.getAttribute("Name")+" Code="+en_region_ele.getAttribute("Code"));
                }

            }
            //System.out.println("=========4、区县名称CN："+cn_region_ele.getAttribute("Name")+"   区县名称EN:    区县Code="+cn_region_ele.getAttribute("Code"));
            int count=region++;

            if ("y".equals(istype)){

                if (properties.getProperty(cn_region_ele.getAttribute("Name"))==null) {
                    System.out.println("=========4、区县 ('zh_CN',\""+cn_region_ele.getAttribute("Name")+"\",'AREA-"+(1*(m+1)+sum*100)+"');");

                    System.out.println("=========4、区县 ('en_US',\""+ToPingyin.toUpperCaseFirstOne(ToPingyin.getPingYin(cn_region_ele.getAttribute("Name")))+"\",'AREA-"+(1*(m+1)+sum*100)+"');");

                }else{
                    System.out.println("=========4、区县 ('zh_CN',\""+cn_region_ele.getAttribute("Name")+"\",'AREA-"+(1*(m+1)+sum*100)+"');");

                    System.out.println("=========4、区县 ('en_US',\""+properties.getProperty(cn_region_ele.getAttribute("Name"))+"\",'AREA-"+(1*(m+1)+sum*100)+"');");

                }


               // System.out.println("=========4、区县 ('en_US',\""+properties.getProperty(cn_region_ele.getAttribute("Name"))+"\",'AREA."+(1*(m+1)+sum*100)+"');");
              //  System.out.println("=========4、('"+(1*(m+1)+sum*100)+"','AREA."+(1*(m+1)+sum*100)+"','3');");
            }else {
               // System.out.println("=========4、没有州的区县 (uuid,'zh_CN',\"" + cn_region_ele.getAttribute("Name") + "\",'CITY." + count + "');");
               // System.out.println("=========4、没有州的区县 (uuid,'en_US',\"" + properties.getProperty(cn_region_ele.getAttribute("Name")) + "\",'CITY." + count + "');");
               // System.out.println("=========4、('"+(1*(m+1)+sum*100)+"','CITY."+count+"','0');");
            }

            // System.out.println("=========4、区县名称CN："+cn_region_ele.getAttribute("Name")+"   区县名称EN: "+ properties.getProperty(cn_region_ele.getAttribute("Name"))+"    区县Code="+cn_region_ele.getAttribute("Code"));
        }
    }



}