package com.fei.web.servlet;

import com.fei.domain.User;
import com.fei.my_convrter.MyConverter;
import com.fei.service.IUserService;
import com.fei.service.impl.IUserServiceImpl;
import com.fei.utils.MailUtil;
import com.fei.utils.RandomStrUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

@WebServlet(name = "userServlet" , urlPatterns = "/user")
public class UserServlet extends BaseServlet {

    private IUserService service = new IUserServiceImpl();

    /**
     * 跳转到注册界面
     */
    public String registUI (HttpServletRequest request, HttpServletResponse response)
    {
        return "/jsp/register.jsp";
    }

    /**
     * 注册用户 获取前台出入的参数信息，存入数据库
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public String registUser(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
    // 验证码相关逻辑
        // 获取前天输入的验证码
        String checkCode = request.getParameter("checkCode");
        String check = (String) request.getSession().getAttribute("checkCode");
        System.out.println("check = "+check);
        System.out.println("checkCOde = " + checkCode);
        // 一个验证码只能用一次
        request.getSession().removeAttribute("checkCode");
        if ((checkCode == null) || (!checkCode.equals(check)))
        {
            request.setAttribute("msg", "验证码有误");
            return "/jsp/register.jsp";
        }
        User user = new User();
        // 获取前台参数
        Map<String, String[]> paraMap = request.getParameterMap();
        // 封装数据之前，先注册转换器 把String类型转为Date类型
        ConvertUtils.register(new MyConverter(), Date.class);
        // 把前端输入的信息封装到user对象中
        BeanUtils.populate(user, paraMap);
        // 给user对象设置uid值
        user.setUid(RandomStrUtil.randUid());
        // 设置激活码的值
        user.setCode(RandomStrUtil.randCode());
        System.out.println(user);
        // 把user的值存入store数据库的user表中
        service.addUser(user);
        String mailMsg =
                "恭喜您注册用户成功，<a href='http:localhost:8080" +request.getContextPath()+
                        "/user?method=activeUser&code="+user.getCode()+"'>点此激活</a>";
        MailUtil.sendMail(user.getEmail(), mailMsg, "用户激活邮件");
        // 将提示信息放在requset域中，在界面显示，提示用户
        String msg = "恭喜"+user.getName()+ ((user.getSex().equals("男"))?"先生":"女士") +"，您的账号注册成功，请前往邮箱激活~~~";
        request.setAttribute("msg", msg);
        return "jsp/msg.jsp";
    }

    /**
     * 激活用户 点击邮件中发过去的超链接之后，进入这个方法进行用户激活
     */
    public String activeUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
        /**
         * 激活用户：
         * 获取用户的激活码
         * 根据激活码获取用户信息
         * 如果用户为空 提示
         * 如果不为空 判断是否已经激活过了
         * 如果激活过了  提示
         * 如果还没有激活过 将数据库中表的state状态跟新为1，将code清空 （code只会使用这一次，防止别人通过code修改用户信息）
         * 激活成功，提示用户激活成功，请登录
         * TODO
         */

        String activeCode = request.getParameter("code");
        User user = service.getUserByCode(activeCode);
        if (user == null)
        {
            request.setAttribute("msg", "用户不存在，请先注册~~~");
            return "/jsp/msg.jsp";
        }
        if (user.getState() == 1)
        {
            request.setAttribute("msg", "该用户已经注册过了,无需再次注册~~~");
            return "/jsp/msg.jsp";
        }
        user.setState(1); // 将状态置为1
        user.setCode(""); // 将code置为空
        service.updateUserMsg(user);
        request.setAttribute("msg",
                "注册成功，<a href='"+request.getContextPath()+"/user?method=loginUI'>点击登录</a>");
        return "/jsp/msg.jsp";
    }


    /**
     * 跳转到登陆界面
     * @return
     */
    public String loginUI(HttpServletRequest request, HttpServletResponse response)
    {
        return "jsp/login.jsp";
    }

    /**
     * 用户登录
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public String userLogin (HttpServletRequest request, HttpServletResponse response) throws
            Exception {
        /**
         * 验证码逻辑
         */
        String codePara = request.getParameter("checkCode") ;
        String sessioonCode = (String) request.getSession().getAttribute("checkCode");
        if (codePara == null || !codePara.equals(sessioonCode))
        {
            request.setAttribute("checkMsg", "验证码有误!!!") ;
            return "/jsp/login.jsp" ;
        }

        // 从前台获取用户名和密码
        String userName = request.getParameter("username") ;
        String passwd = request.getParameter("password") ;
        // 通过输入的用户名在数据库中查询对应的用户
        User user = service.getUserByUsername(userName) ;
        // 判断用户是否存在
        if (user == null)
        {
            request.setAttribute("checkMsg", "该用户不存在");
            return "/jsp/login.jsp";
        }
        // 如果用户存在，判断用户是否激活
        if (user.getState() == 0){
            request.setAttribute("checkMsg", "您的账户尚未激活，请前往邮箱激活");
            return "/jsp/login.jsp";
        }

        // 如果用户存在，检查密码是否正确
        if (passwd == null || !passwd.equals(user.getPassword()))
        {
            request.setAttribute("checkMsg", "用户名和密码不匹配");
            return "jsp/login.jsp";
        }

        /**
         * 登录之后 将登陆状态设置为true 在界面判断如果登陆了就将
         */
        // 登录之后将当前用户存到session中
        request.getSession().setAttribute("loginUser", user);
        // 将状态设置为登陆状态
        request.setAttribute("loginState", "Yes");
        return "/jsp/index.jsp" ;
    }

    /**
     * 用户退出方法
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public String userExit(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        request.setAttribute("loginState", null);
        return "/jsp/index.jsp" ;
    }

}
