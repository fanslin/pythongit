package com.ffcs.grid;

import android.widget.ImageView;
import android.widget.TextView;

//���ڱ���GridView����헵����ݵ���
public class GridInfo {

	private String name;

	// ��Item��ͼƬid����ʼ����ʱ��̶�ָ��ֵ��Ҳ����ΪΨһ��ʶ
	private int image_id;

	private String img;

	// ����imageviewʵ��
	private ImageView imgView;

	// ����textviewʵ��
	private TextView txtView;

	public GridInfo(String name, int image_id) {

		super();

		this.name = name;

		this.image_id = image_id;

		// ����Ǳ���ģ�ͼ���������ʽ��grid_integer;
		this.img = "grid_" + image_id;
	}

	public String getName() {
		return name;
	}

	public void setImgView(ImageView imgView) {
		this.imgView = imgView;
	}

	public ImageView getImgView() {
		return imgView;
	}

	public void setTxtView(TextView txtView) {
		this.txtView = txtView;
	}

	public TextView getTxtView() {
		return txtView;
	}

	public void setImage_id(int id) {
		this.image_id = id;
	}

	public int getImage_id() {
		return image_id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}
}
