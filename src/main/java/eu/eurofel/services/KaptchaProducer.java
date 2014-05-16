package eu.eurofel.services;

import com.google.code.kaptcha.Producer;

public interface KaptchaProducer extends Producer {

	int getWidth();

	int getHeight();

}
