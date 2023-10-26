package jp.co.sss.sys.form;

/**
 * フォームクラス
 * @author Inoue Nami
 *
 */
public class LoginForm {
	/**
	 * 社員番号
	 */
	private String empId;
	/**
	 * パスワード
	 */
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
