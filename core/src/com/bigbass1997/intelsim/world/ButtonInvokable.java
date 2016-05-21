package com.bigbass1997.intelsim.world;

import java.util.concurrent.Callable;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class ButtonInvokable extends TextButton{
	
	public Callable<?> clickCallback;
	
	public ButtonInvokable(String text, Skin skin, Callable<?> callable){
		super(text, skin);
		this.clickCallback = callable;
		this.toggle();
	}
	
	@Override
	public void act(float delta){
		if(isPressed()){
			try {
				clickCallback.call();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
}
