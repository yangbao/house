package com.mooc.house.web.interceptor;

import com.mooc.house.common.model.User;
/**
 * 利用ThreadLocal管理登录用户信息实现随用随取
 * ThreadLocal，顾名思义，就是本地线程，可是这个名字实在容易让人误解，因为其实它是本地线程局部变量的意思，
 * 首先我们要知道，我们每个请求都会对应一个线程，这个ThreadLocal就是这个线程使用过程中的一个变量，该变量为其所属线程所有，各个线程互不影响。
 * 所以我们可以借助这个ThreadLocal来存储登录用户的信息，在一个请求中，所有调用的方法都在同一个线程中去处理，
 * 这样就实现了在任何地方都可以获取到用户信息了， * 从而摆脱了HttpServletRequest的束缚
 * 可是这时候又出现了问题，在一个线程结束后，我又发起了一次后台请求，这个时候，处理这个请求的线程变成了另外一个线程，线程切换了！！！
 * 而这个线程中我们并没有set用户信息到它的ThreadLocal中去，此时我们想要获取用户信息就获取不到了，前面说过，ThreadLocal为各个线程所私有，
 * 各线程间不共享，也互不影响，那么问题来了，我们只是在登录的时候，查询用户信息并将其放进当前线程的ThreadLocal，而后续其它请求一旦切换到别的线程，我们的功能就玩不转了，
 * 所以我们需要借助一个方法来过滤所有的后台请求（排除非必须登录才能访问的url），给用户信息做个检查，
 * 一旦SessionLocal.getUser()为空，那么我们就set进去，so，我们可以借助一个Filter来达到我们的目的
 * @author u6035457
 *
 */
public class UserContext {
	
	private static final ThreadLocal<User> USER_HODLER = new ThreadLocal<>();
    
	public static void setUser(User user){
		USER_HODLER.set(user);
	}
	
	public static void remove(){
		USER_HODLER.remove();
	}
	
	public static User getUser(){
		return USER_HODLER.get();
	}
}
