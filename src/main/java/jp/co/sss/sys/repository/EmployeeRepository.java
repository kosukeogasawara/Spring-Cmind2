//CRUD = 登録・削除・更新・参照機能
//このクラスでテーブルが存在するか確認している

package jp.co.sss.sys.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.sss.sys.entity.Employee;

/**
 * リポジトリークラス
 * @author Inoue Nami
 *
 */
public interface EmployeeRepository extends JpaRepository<Employee, String> {

    Employee findByEmpIdAndPassword(String empId, String password);
    
    List<Employee> findAll();

    Employee findByEmpId(String empId);

}