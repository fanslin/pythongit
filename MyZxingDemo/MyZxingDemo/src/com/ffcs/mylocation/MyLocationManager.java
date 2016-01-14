package com.ffcs.mylocation;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.TextView;
import android.widget.Toast;

public class MyLocationManager {
	// ��ȡ��λ����
	private LocationManager mgr;
	// λ�÷����ṩ��
	private String serviceName = Context.LOCATION_SERVICE;
	// Ĭ��ΪGPS
	private String provider ;
	// ����λ����Ϣ
	private Location l;
	// ����λ����Ϣ����γ�ȣ�
	double lat;
	double lng;
	private Context context;

	// ͨ�������ȡ�͵���ϵͳλ�÷���
	public MyLocationManager(Context context) {
		this.context = context;
		initLocation();// ��ʼ��
	}

	// ��ʼ��
	private void initLocation() {

		ConnectivityManager mConnectivity = (ConnectivityManager)this.context.getSystemService(Context.CONNECTIVITY_SERVICE);
		 NetworkInfo info = mConnectivity.getActiveNetworkInfo();
		 //�ж������Ƿ���
		 if(info != null && mConnectivity.getBackgroundDataSetting()) {
			// ��ȡ��LocationManager����
				mgr = (LocationManager) this.context.getSystemService(serviceName);

				// �ж϶�λ��ʽ
				openGPSSettings();

				// ���ݵ�ǰprovider�����ȡ���һ��λ����Ϣ
				Location location = mgr.getLastKnownLocation(provider);
				// ����λ����Ϣ
				updateToNewLocation(location);
				//����λ�ñ仯��2��һ�Σ�����10������
		        mgr.requestLocationUpdates(provider, 2000, 10, mylocationlistener);
		 }else{
			 Toast.makeText(this.context, "������������", Toast.LENGTH_SHORT).show();
		 }

//		if(location == null){
//			mgr.requestLocationUpdates(provider, 0, 0, mylocationlistener);
//		}
	

//		// ���ü��������Զ����µ���Сʱ��Ϊ���N��(1��Ϊ1*1000������д��ҪΪ�˷���)����Сλ�Ʊ仯����N��
//		mgr.requestLocationUpdates(provider, 5 * 1000, 500, mylocationlistener);

	}

	// �ж϶�λ��ʽ
	private void openGPSSettings() {	
			if (mgr.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
				Toast.makeText(this.context, "GPS�ѿ���", Toast.LENGTH_SHORT).show();
				 Criteria criteria = new Criteria();
			        criteria.setAccuracy(Criteria.ACCURACY_FINE);//�߾���
			        criteria.setAltitudeRequired(false);//��Ҫ�󺣰�
			        criteria.setBearingRequired(false);//��Ҫ��λ
			        criteria.setCostAllowed(true);//�����л���
			        criteria.setPowerRequirement(Criteria.POWER_LOW);//�͹���
			      //�ӿ��õ�λ���ṩ���У�ƥ�����ϱ�׼������ṩ��
			        provider = mgr.getBestProvider(criteria, true);			        
			        return;
			}
			Toast.makeText(this.context, "GPSδ������ϵͳ��ʹ�û�վ��λ", Toast.LENGTH_SHORT).show();
			provider = LocationManager.NETWORK_PROVIDER; //��GPSδ�������û�վ��λ
			return;	
	}

	// ��������
		private void updateToNewLocation(Location l) {

			/*if (provider.equals(LocationManager.GPS_PROVIDER)) {
				try {
					// �����ȡλ����Ϣ��Ҫһ����ʱ�䣬Ϊ�˱�֤��ȡ������ȡϵͳ���ߵ��ӳٷ���
					Thread.sleep(0);
				} catch (InterruptedException e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				}
			}*/
			while(true){
				l = mgr.getLastKnownLocation(provider);
			if (l != null) {
				lat = l.getLatitude();//��ȡγ��
				lng = l.getLongitude();//��ȡ����
				// ������ַ����ʾ
				/*Geocoder geoCoder = new Geocoder(this.context);
				List<Address> list = null;
				try {
					list = geoCoder.getFromLocation(lat, lng, 2);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				for (int i = 0; i < list.size(); i++) {
					Address address = list.get(i);
					Toast.makeText(this.context, address.getAdminArea()+ address.getLocality()+ address.getFeatureName(), Toast.LENGTH_LONG).show();
				}*/
				break;
			} else {
				Toast.makeText(this.context, "�޷���ȡ������Ϣ���������ã�", Toast.LENGTH_SHORT).show();
			}
			}
		}

	// ����λ�ñ仯
	private MyLocationListener mylocationlistener = new MyLocationListener();

	private class MyLocationListener implements LocationListener {
		// ������λ�øı�ʱ�����˺���
		@Override
		public void onLocationChanged(Location loc) {
			// TODO Auto-generated method stub
			updateToNewLocation(loc);
			/*if (count < 5 && l != null) {
				lats[count] = lat;
				lngs[count] = lng;
				count++;
			}
			if (count == 5) {
				getExactly();
				count = 0;
			}*/
		}

		// Provider����ʱ����������GPS�ر�
		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			updateToNewLocation(l);
		}

		// Provider����ʱ����������GPS����
		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub

		}

		// Provider��ת̬�ڿ��á���ʱ�����ú��޷�������״ֱ̬���л�ʱ�����˺���
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
		}
	}

	// ��ȡ��ȷ�ľ�γ��
	double lats[] = { 0, 0, 0, 0, 0 }, lngs[] = { 0, 0, 0, 0, 0 };
	// ��¼λ�ñ仯�Ĵ���
	int count = 0;

	private void getExactly() {
		if (provider.equals(LocationManager.NETWORK_PROVIDER)) {
			double avg_lat = 0.0, avg_lng = 0.0;
			for (int i = 0; i < 5; i++) {
				avg_lat += lats[i];
				avg_lng += lngs[i];
			}
			avg_lat /= 5.0;
			avg_lng /= 5.0;
			double[] flag = { 0, 0, 0, 0, 0 };
			for (int i = 0; i < 5; i++) {
				flag[i] = avg_lat + avg_lng - lats[i] - lngs[i];
			}
			double min = flag[0];
			int pos = 0;
			for (int i = 1; i < 5; i++) {
				if (min < flag[i]) {
					pos = i;
				}
			}
			this.lat = lats[pos];
			this.lng = lngs[pos];
		}
	}
	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}
}