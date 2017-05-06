package com.shengui.app.android.shengui.controller;


import org.greenrobot.eventbus.EventBus;

/****
 * 时间总线管理器
 * @author HW
 *
 */
public class EventManager {

	private static EventManager manager;
	private EventBus eventBus;
	
	private EventManager() {
		eventBus = EventBus.getDefault();
	}
	
	public static EventManager getInstance(){
		if(null==manager){
			manager = new EventManager();
		}
		return manager;
	}
	
	
	public void register(Object subscriber){
		if(isRegister(subscriber))
			return;
		this.eventBus.register(subscriber);
	}
	
//	public void register(Object subscriber,int priority){
//		if(isRegister(subscriber))
//			return;
//		this.eventBus.register(subscriber,priority);
//	}
	
//	public void registerSticky(Object subscriber){
//		if(isRegister(subscriber))
//			return;
//		this.eventBus.registerSticky(subscriber);
//	}
//
//	public void registerSticky(Object subscriber,int priority){
//		if(isRegister(subscriber))
//			return;
//		this.eventBus.registerSticky(subscriber,priority);
//	}
	
	public void unregister(Object subscriber){
		this.eventBus.unregister(subscriber);
	}
	
	
	public void post(Object event){
		this.eventBus.post(event);
	}
	
	public void postSticky(Object event){
		this.eventBus.postSticky(event);
	}
	
	public boolean isRegister(Object subscriber){
		return this.eventBus.isRegistered(subscriber);
	}
	
	
	
	
}
