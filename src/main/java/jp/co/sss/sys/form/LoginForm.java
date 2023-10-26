package jp.co.sss.sys.form;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * フォームクラス
 * @author Inoue Nami
 *
 */
public class LoginForm {
	/**
	 * 社員番号
	 */
    @NotBlank(message = "社員番号は入力必須項目です。")
    @Size(max = 5, message = "社員番号は5文字以内で入力してください。")
	private String empId;
	/**
	 * パスワード
	 */
    @NotBlank(message = "パスワードは入力必須項目です。")
    @Size(max = 16, message = "パスワードは16文字以内で入力してください。")
	private String password;

	//ゲッターセッター
	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
