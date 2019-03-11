package com.beeworkshop.spaback.commons.system;

import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * 
 * @author beeworkshop
 * @description 设置默认重定向至前端首页
 * 
 *              spring boot拦截器WebMvcConfigurerAdapter的替代方案
 */
public class WebMvcConfig extends WebMvcConfigurationSupport {
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("forward:/index.html");
		registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
		super.addViewControllers(registry);
	}
}

//@formatter:off
/*
 * 
 * 请求转发（Forward）和重定向（Redirect）的区别
 * 
 * forward（转发）：
 * 
 * 是服务器请求资源,服务器直接访问目标地址的URL,把那个URL的响应内容读取过来,然后把这些内容再发给浏览器.
 * 浏览器根本不知道服务器发送的内容从哪里来的,
 * 因为这个跳转过程实在服务器实现的，并不是在客户端实现的所以客户端并不知道这个跳转动作，
 * 所以它的地址栏还是原来的地址.
 * 
 * redirect（重定向）：
 * 
 * 是服务端根据逻辑,发送一个状态码,告诉浏览器重新去请求那个地址.所以地址栏显示的是新的URL.
 * 
 * 转发是服务器行为，重定向是客户端行为。
 * 
 * 区别：
 * 
 * 1. 从地址栏显示来说 forward是服务器请求资源,服务器直接访问目标地址的URL,
 * 把那个URL的响应内容读取过来,然后把这些内容再发给浏览器.
 * 浏览器根本不知道服务器发送的内容从哪里来的,所以它的地址栏还是原来的地址.
 * 
 * redirect是服务端根据逻辑,发送一个状态码,告诉浏览器重新去请求那个地址.所以地址栏显示的是新的URL.
 * 
 * 2. 从数据共享来说 forward:转发页面和转发到的页面可以共享request里面的数据. redirect:不能共享数据.
 * 
 * 3. 从运用地方来说 forward:一般用于用户登陆的时候,根据角色转发到相应的模块.
 * redirect:一般用于用户注销登陆时返回主页面和跳转到其它的网站等
 * 
 * 4. 从效率来说 forward:高. redirect:低.
 * 
 * 本质区别：
 * 
 * 解释一：
 * 
 * 一句话，转发是服务器行为，重定向是客户端行为。为什么这样说呢，这就要看两个动作的工作流程：
 * 
 * 转发过程：客户浏览器发送http请求----》web服务器接受此请求--》调用内部的一个方法在容器内部完
 * 成请求处理和转发动作----》将目标资源发送给客户
 * ;在这里，转发的路径必须是同一个web容器下的url，其不能转向到其他的web路径上去，
 * 中间传递的是自己的容器内的request。
 * 在客户浏览器路径栏显示的仍然是其第一次访问的路径，也就是说客户是感觉不到服务器做了转发的。
 * 转发行为是浏览器只做了一次访问请求。
 * 
 * 重定向过程：客户浏览器发送http请求----》web服务器接受后发送302状态码响应及对应新的location给客户浏览器--》
 * 客户浏览器发现是302响应，则自动再发送一个新的http请求，请求url是新的location地址----》服务器根据此请求
 * 寻找资源并发送给客户。在这里
 * location可以重定向到任意URL，既然是浏览器重新发出了请求，则就没有什么request传递的概念了。
 * 在客户浏览器路径栏显示的是其重定向的路径，
 * 客户可以观察到地址的变化的。重定向行为是浏览器做了至少两次的访问请求的。
 * 
 * 解释二：
 * 
 * 重定向，其实是两次request, 第一次，客户端request
 * A,服务器响应，并response回来，告诉浏览器，你应该去B。这个时候IE可以看到地址变了，而且历史的回退按钮也亮了。
 * 重定向可以访问自己web应用以外的资源。在重定向的过程中，传输的信息会被丢失。
 * 
 * 例子： 请求转发是服务器内部把对一个request/response的处理权，移交给另外一个
 * 对于客户端而言，它只知道自己最早请求的那个A，而不知道中间的B，甚至C、D。 传输的信息不会丢失。
 * 
 * 解释三：
 * 
 * 转发是服务器行为，重定向是客户端行为。
 * 
 * 
 * 两者的内部机制有很大的区别： 
 * 1 请求转发只能将请求转发给同一个WEB应用中的组件，
 * 而重定向还可以重新定向到同一站点不同应用程序中的资源，甚至可以定向到一绝对的URL。 
 * 2 重定向可以看见目标页面的URL，
 * 转发只能看见第一次访问的页面URL，以后的工作都是有服务器来做的。 
 * 3 请求响应调用者和被调用者之间共享相同的request对象和response对象，
 * 重定向调用者和被调用者属于两个独立访问请求和响应过程。
 *  4 重定向跳转后必须加上return，要不然页面虽然跳转了， 但是还会执行跳转后面的语句，
 * 转发是执行了跳转页面，下面的代码就不会在执行了。
 * 
 */