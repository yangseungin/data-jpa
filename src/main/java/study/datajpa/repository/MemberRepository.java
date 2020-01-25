package study.datajpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom, JpaSpecificationExecutor<Member> {

    List<Member> findByUserNameAndAgeGreaterThan(String username, int age);

    List<Member> findHelloBy();

    List<Member> findTop3HelloBy();

    //    주석처리가능
//    @Query(name = "Member.findByUserName")
    List<Member> findByUserName(@Param("user Name") String userName);

    @Query("select m from Member m where m.userName= :userName and m.age = :age")
    List<Member> findUser(@Param("userName") String userName, @Param("age") int age);

    @Query("Select m.userName from Member m")
    List<String> findUsernameList();

    @Query("select new study.datajpa.dto.MemberDto(m.id, m.userName, t.name) from Member m join m.team t")
    List<MemberDto> findMemberDto();

    @Query("select m from Member m where m.userName in :names")
    List<Member> findByNames(@Param("names") Collection<String> names);

    List<Member> findListByUserName(String userName); // collection

    Member findMemberByUserName(String userName);   // 단건

    Optional<Member> findOptionalByUserName(String userName); //단건 optional

    @Query(value = "select m from Member m left join m.team t",
            countQuery = "select count(m.userName) from Member m")
    Page<Member> findByAge(int age, Pageable pageable);

    @Modifying(clearAutomatically = true)
    @Query("update Member m set m.age = m.age + 1 where m.age >= :age")
    int bulkAgePlus(@Param("age") int age);

    @Query("select m from Member m left join fetch m.team")
    List<Member> findMemberFetchJoin();

    @Override
    @EntityGraph(attributePaths = {"team"})
    List<Member> findAll();

    @EntityGraph(attributePaths = {"team"})
    @Query("select m from Member m")
    List<Member> findMemberEntityGraph();

    @EntityGraph(attributePaths = ("team"))
    List<Member> findEntityGraphByUserName(@Param("userName") String userName);

    @QueryHints(value = @QueryHint(name = "org.hibernate.readOnly", value = "true"))
    Member findReadOnlyByUserName(String userName);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<Member> findLockByUserName(String userName);
    

}
