package com.ncu.springboot.provider.message.entity.validatecode;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

public class ImageCode extends ValidateCode {

	private BufferedImage image;

	public ImageCode(BufferedImage image, String code, int expireTime) {
		super(code, LocalDateTime.now().plusSeconds(expireTime));
		this.image = image;
	}

	public ImageCode(BufferedImage image, String code, LocalDateTime localDateTime) {
		super(code, localDateTime);
		this.image = image;
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

}
