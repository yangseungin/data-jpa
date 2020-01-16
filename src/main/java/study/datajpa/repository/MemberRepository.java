package study.datajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import study.datajpa.entity.Member;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findByUserNameAndAgeGreaterThan(String username, int age);

    List<Member> findHelloBy();

    List<Member> findTop3HelloBy();

    //    주석처리가능
//    @Query(name = "Member.findByUserName")
    List<Member> findByUserName(@Param("user Name") String userName);

    @Query("select m from Member m where m.userName= :userName and m.age = :age")
    List<Member> findUser(@Param("userName") String userName, @Param("age") int age);

}
