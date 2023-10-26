package jp.co.sss.sys.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jp.co.sss.sys.entity.Employee;
import jp.co.sss.sys.form.LoginForm;
import jp.co.sss.sys.repository.EmployeeRepository;

/**
 * コントローラークラス
 * @author Inoue Nami
 *
 */
@Controller
public class IndexController {

	@Autowired
	EmployeeRepository empRepository;
	
        
	/**
	 * ログイン画面を表示する
	 * @param loginForm
	 * @return ログイン画面
	 */
	@GetMapping(path = "/login")
	public String login(LoginForm loginForm) {
		return "login";		
	}
	
	/**
	 * ログアウト処理を行う
	 * 
	 */
	@GetMapping(path = "/logout")
	public String logout(HttpServletRequest request) {
	    HttpSession session = request.getSession(false);//セッションを取得（存在した場合は作成しない）
	    if(session != null) {
	        session.invalidate();//セッションを無効に（破棄）
	    }
	    return "redirect:/login";
	}
	/**
	 * 入力された値を元にログイン認証し、トップ画面に遷移する
	 * @param loginForm
	 * @param req
	 * @param res
	 * @return トップ画面
	 */
	@PostMapping(path = "/top")
	public String login(@Validated LoginForm loginForm, BindingResult result, HttpServletRequest req, HttpServletResponse res, Model model, HttpServletRequest request, HttpSession session) {
	    if (result.hasErrors()) {
	        // バリデーションエラーがある場合
	        return "login";
	    }
	    
	    try {
	        
	    //インライン化
	    Employee employee = empRepository.findByEmpIdAndPassword(loginForm.getEmpId(), loginForm.getPassword());
	    
	    if (employee == null) {
	        // ログインエラー：該当データが見つからない場合
	        model.addAttribute("errorMessage", "社員番号またはパスワードが違います");
	        return "login";
	    }
	    
	    //名前を取得して表示
        session.setAttribute("empName", employee.getEmpName());

        
	   
	    //合致する情報が存在した場合、データを一覧表示
	    List<Employee> employeelist = empRepository.findAll();
        model.addAttribute("employeelist", employeelist);
	        
	        return "top";
	        
	    }catch (DataAccessException e) {
            return "error";
        }
	}

    /**
	 * 社員一覧項目ページへ遷移
	 * @param model
	 * @param employee
	 * @param empId
	 * @return 社員一覧画面
	 */
	@GetMapping(path = "/employee")
	public String employee(@ModelAttribute Employee employee, String empId, Model model) {
	    
	  //通常処理
        List<Employee> employees = empRepository.findAll();
        model.addAttribute("employees", employees);
        
	    return "employee";
	}
	
	/**
     * マイページ画面へ遷移
     * @param employee
     * @return マイページ画面
     */
	@GetMapping(path = "/mypage")
    public String myPage(Employee employee, Model model, HttpSession session) {
        
        return "mypage";
    }
	
	/**
	 * 更新画面で入力された値を元に、社員情報を更新
	 * @param updateEmployee 
	 * @return 更新完了画面
	 */
	@PostMapping(path = "/complete")
	public String updateMypage(@ModelAttribute @Validated Employee updateEmployee, BindingResult result, Model model) {
	  //入力チェック
        if(result.hasErrors()) {
            return "mypage";
        }
        
	    Employee employee = empRepository.findByEmpId(updateEmployee.getEmpId());
	    //該当データが存在しないもしくは更新ができなかった場合、error画面へ遷移
	    if(employee == null) {
	        return "error";
	    }
	    
	        // フォームから送信されたデータでエンティティを更新
	        employee.setEmpId(updateEmployee.getEmpId());
	        //System.out.println("EmpId: " + updateEmployee.getEmpId());

	        employee.setEmpName(updateEmployee.getEmpName());
	        //System.out.println("EmpName: " + updateEmployee.getEmpName());

	        employee.setPassword(updateEmployee.getPassword());
	        //System.out.println("Password: " + updateEmployee.getPassword());
	        
	        employee.setGender(updateEmployee.getGender());
            //System.out.println("Gender: " + updateEmployee.getGender());
            
	        
	        employee.setBirthday(updateEmployee.getBirthday());
	        //System.out.println("Birthday" + updateEmployee.getBirthday());
	        
            // エンティティを保存
            empRepository.save(employee);
            
            model.addAttribute("employee", employee);
	    
	    //更新が完了した場合
	    return "complete";
	}
	
	/**
	 * 更新完了画面からマイページ画面へ
	 * @param model
	 * @return マイページ画面
	 */
	@PostMapping(path = "/mypage")
	public String returnMypage(Model model) {
	    return "redirect:/mypage";
	}
}

