/**
 * 
 */
package com.edroplet.rent.beans;

import java.io.Serializable;

/**
 * @name HandPickedDetailData
 * @Descripation <br>
 *		1、精选内容实体的设计<br>
 *		2、<br>
 *      3、<br>
 * @author edroplet
 * @date 2018-04-06
 * @version 1.0
 */
public class HandPickedDetailData implements Serializable {
	private static final long serialVersionUID = 642225199212241050L;
	private String image;
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getPoi() {
		return poi;
	}
	public void setPoi(String poi) {
		this.poi = poi;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	private String poi;
	private String text;

}
