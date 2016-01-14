package com.ffcs.activity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.xml.sax.Attributes;

import com.ffcs.mylocation.MyLocationManager;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.sax.Element;
import android.sax.EndElementListener;
import android.sax.EndTextElementListener;
import android.sax.RootElement;
import android.sax.StartElementListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class ResultActivity extends Activity {
	
	private String result = " \n";
	private Button btn_Return = null;
	private Button btn_Top = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.result);
//		String urlPath = CaptureActivity.getUrlGet();
//		EditText etGet = (EditText) findViewById(R.id.etGet); // ���EditText����
//		etGet.setText(url);
	//  getAccount(url);
		MyLocationManager mgr = new MyLocationManager(this);
		String loc =  mgr.getLat() + "," + mgr.getLng();
		EditText result = (EditText) findViewById(R.id.View); // ���EditText����
		result.setText(loc);
		result.setTextSize(18);
		
		/*//��ȡ����xml
		try {

//            String xml = HttpClientUtil.get(urlPath, "requestData", null);
//            String xml = getContent(urlPath, "UTF-8");
			Map paraMap = new HashMap();
			paraMap.put("m", "lg_active");
			paraMap.put("c", "index");
			paraMap.put("a", "checkcode");
			paraMap.put("key", "123456");
			paraMap.put("code", "283c1b72b28f36f316e7161eb34aa3a6");
			String xmlString = HttpRequestUtil.doGet(urlPath, paraMap, "UTF-8");
			XMLParser xmlParser = new XMLParser(xmlString.trim());
			String name = xmlParser.getNodeValue("/root/persons/person/name");
			String sex = xmlParser.getNodeValue("/root/persons/person/sex");
			String company = xmlParser.getNodeValue("/root/persons/person/company");
			String email = xmlParser.getNodeValue("/root/persons/person/email");
			String starttime = xmlParser.getNodeValue("/root/persons/person/starttime");
			String address = xmlParser.getNodeValue("/root/persons/person/address");
			String number = xmlParser.getNodeValue("/root/persons/person/number");
			String mid = xmlParser.getNodeValue("/root/persons/person/mid");
			String meeting = xmlParser.getNodeValue("/root/persons/person/meeting");
			String code = xmlParser.getNodeValue("/root/persons/person/code");
			String id = xmlParser.getNodeValue("/root/persons/status/id");
			String des = xmlParser.getNodeValue("/root/persons/status/des");
//			Person per = new Person();
			person.setId(id);
			person.setName(name);
			person.setSex(sex);
			person.setCompany(company);
			person.setEmail(email);			
			person.setDes(des);
			person.setStarttime(starttime);
			person.setAddress(address);
			person.setNumber(number);
			person.setMid(mid);
			person.setMeeting(meeting);
			person.setCode(code);
			result += person.toString() + "\n";
            
            FileOutputStream out = new FileOutputStream(new File(
					Environment.getExternalStorageDirectory(), "Person.xml")); 
            FileOutputStream out = openFileOutput(fileName,  
                    Activity.MODE_PRIVATE);  

			OutputStreamWriter writer = new OutputStreamWriter(out, "UTF-8");
			writer.write(xml);
			writer.flush();
			writer.close();
			out.close();

   //         InputStream inputStream = this.getClassLoader().getResourceAsStream("/Person.xml");
			InputStream inputStream= this.openFileInput("Person.xml");
           
            SAXParserFactory factory = SAXParserFactory.newInstance();

            SAXParser parser = factory.newSAXParser();

            XMLReader reader = parser.getXMLReader();

            reader.setContentHandler(getRootElement().getContentHandler());

            reader.parse(new InputSource(inputStream));	


            Log.i("֪ͨ", "�������");

        }catch (Exception e) {

            e.printStackTrace();

        }

        for(Person person:PersonList){ 

            result += person.toString() + "\n";
             

        }

        TextView textView = (TextView)findViewById(R.id.textView);
        
        textView.setText(result);
        
        textView.setTextSize(18);
        
        if("1".equals(person.getId())){
        	
        	textView.setTextColor(Color.GREEN);
        	
        }else {
        	
        	textView.setTextColor(Color.RED);
        }
        
     
}
	
	class ButtonListener implements OnClickListener {

		@Override
		public void onClick(View view) {
			
			Intent intent = new Intent();
			
			if (view.getId() == R.id.btnReturn) {
								
				intent.setClass(ResultActivity.this, CaptureActivity.class);
				
				ResultActivity.this.startActivity(intent);
				ResultActivity.this.finish();

			}else{
								
				intent.setClass(ResultActivity.this, FirstActivity.class);
				
				ResultActivity.this.startActivity(intent);
				ResultActivity.this.finish();
				
			}			
		}}
	
	public static String getContent(String urlPath, String encoding)
			throws Exception {
		// TODO Auto-generated method stub
		URL url = new URL(urlPath);
		HttpURLConnection conn;
		try {
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(6 * 1000);
			if (conn.getResponseCode() == 200) {

				// ����һ������������ȡ�ӷ��������ص�����
				InputStream inStream = conn.getInputStream();
				// ����readStream���������������д������ص�һ���ֽ����顣
				byte[] data = readStream(inStream);
				// �õ�����ֵ��
				// ���ַ�����ʽ�ķ��ء�
				return new String(data, encoding);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
	public static byte[] readStream(InputStream inStream) throws Exception {
		// readStream����˴��ݽ�����������
		// Ҫ����������������Ҫ����һ���������
		// ����һ���ֽ������͵��������ByteArrayOutputStream
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		// ����һ��������buffer
		byte[] buffer = new byte[1024];
		int len = -1;
		// �����������ϵĶ������ŵ���������ȥ��ֱ������
		while ((len = inStream.read(buffer)) != -1) {
			// �������������ݲ��ϵ�д���ڴ���ȥ���߶���д
			outStream.write(buffer, 0, len);
		}
		outStream.close();
		inStream.close();
		// ����������ֽ�����ķ�ʽ����
		return outStream.toByteArray();
	}
	
	private RootElement getRootElement() {
		// TODO Auto-generated method stub
		RootElement rootElement = new RootElement("persons");
		Element personElement = rootElement.getChild("person");
		Element _personElement = rootElement.getChild("status");
		// ����Ԫ�ؿ�ʼλ��ʱ�����������<person>ʱ
		personElement.setStartElementListener(new StartElementListener(){
			@Override
            public void start(Attributes attributes) {

                // TODO Auto-generated method stub

                Log.i("֪ͨ", "start");

                person = new Person();

            }
			
		});
		//����Ԫ�ؽ���λ��ʱ�����������</person>ʱ
		personElement.setEndElementListener(new EndElementListener() {

            @Override

            public void end() {

                PersonList.add(person);

            }

        });
		 Element nameElement = personElement.getChild("name");
		// �����ı���ĩβʱ����,�����vip��Ϊ�ı������ݲ���

	        nameElement.setEndTextElementListener(new EndTextElementListener() {

	            @Override

	            public void end(String vip) {

	                person.setName(vip);

	            }

	        });
	        Element companyElement = personElement.getChild("company");

	        companyElement.setEndTextElementListener(new EndTextElementListener() {

	            @Override

	            public void end(String vip) {

	                person.setCompany(vip);

	            }

	        });
	        Element emailElement = personElement.getChild("email");

	        emailElement.setEndTextElementListener(new EndTextElementListener() {

	            @Override

	            public void end(String vip) {

	                person.setEmail(vip);

	            }

	        });
	        Element starttimeElement = personElement.getChild("starttime");

	        starttimeElement.setEndTextElementListener(new EndTextElementListener() {

	            @Override
				public void end(String vip) {
					
					person.setStarttime(vip);
	            	
				}

	        });
	        Element addressElement = personElement.getChild("address");

	        addressElement.setEndTextElementListener(new EndTextElementListener() {

	            @Override

	            public void end(String vip) {

	                person.setAddress(vip);

	            }

	        });
	        Element numberElement = personElement.getChild("number");

	        numberElement.setEndTextElementListener(new EndTextElementListener() {

	            @Override

	            public void end(String vip) {

	                person.setNumber(vip);

	            }

	        });
	        Element codeElement = personElement.getChild("code");

	        codeElement.setEndTextElementListener(new EndTextElementListener() {

	            @Override

	            public void end(String vip) {

	                person.setCode(vip);

	            }

	        });
	        Element sexElement = personElement.getChild("sex");

	        sexElement.setEndTextElementListener(new EndTextElementListener() {

	            @Override

	            public void end(String vip) {

	                person.setSex(vip);

	            }

	        });
	        Element midElement = personElement.getChild("mid");

	        midElement.setEndTextElementListener(new EndTextElementListener() {

	            @Override

	            public void end(String vip) {

	                person.setMid(vip);

	            }

	        });
	        Element meetingElement = personElement.getChild("meeting");

	        meetingElement.setEndTextElementListener(new EndTextElementListener() {

	            @Override

	            public void end(String vip) {

	                person.setMeeting(vip);

	            }

	        });
	        Element idElement = _personElement.getChild("id");

	        idElement.setEndTextElementListener(new EndTextElementListener() {

	            @Override

	            public void end(String vip) {

	                person.setId(vip);

	            }

	        });
	        Element desElement = _personElement.getChild("des");

	        desElement.setEndTextElementListener(new EndTextElementListener() {

	            @Override

	            public void end(String vip) {

	                person.setDes(vip);

	            }

	        });
	        
		return rootElement;*/
	}
 }

