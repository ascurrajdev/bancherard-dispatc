package py.com.sodep.bancard.repository;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import py.com.sodep.bancard.domain.TransactionHistoryEntity;

@Repository
public interface ITransactionHistoryRepository extends JpaRepository<TransactionHistoryEntity, Long> {

	@Query(value = "select * from bancard.transaction_history where service_name =:serviceName and product =:product and amt =:amt and sub_id=:subId and created >= CAST(now() AS DATE) + interval '1h'", nativeQuery=true)
	List<TransactionHistoryEntity> findByServiceNameAndProductAndSubIdAndAmt(@Param("serviceName") String serviceName, @Param("product") String product, @Param("subId") String subId, @Param("amt") Integer amt);

	@Query(value = "update bancard.transaction_history set status = 1 where tid =:tid", nativeQuery=true)
	void reverseTransactionByTid(@Param("tid") BigInteger tid);
	
	@Query(value = "select * from bancard.transaction_history where service_name=:serviceName and product =:product and sub_id =:subId and inv_id =:invId", nativeQuery=true)
	List<TransactionHistoryEntity> selectTransactionsByInvId(@Param("serviceName") String serviceName, @Param("product") String product, 
			@Param("subId") String subId, @Param("invId") String invId);

	TransactionHistoryEntity findByTransactionId(Long tid);	
	
	List<TransactionHistoryEntity> findByServiceNameAndProductAndCreatedBetweenAndInformed(String serviceName, String product, Date start, Date end, Boolean informed, Sort sort);

	/*
	** Excluye los atributos:
	** id, created, tid, service_name y product 
	*******************************************/
	@Modifying(clearAutomatically = true)
	@Transactional
	@Query(value = "UPDATE TransactionHistoryEntity t"
			+ "   SET t.additionalData=:addl,      t.amt=:amt,       t.productId=:prdId,"
			+ "       t.commissionCurr=:cmCurr,    t.curr=:curr,     t.informed=:informed,"
			+ "       t.commissionAmt=:cmAmt,      t.subId=:subId,   t.invId=:invId,"
			+ "       t.transactionDate=:txDate,   t.type=:type,     t.billerData=:billerData,"
			+ "       t.transactionHour=:txHour "
			+ " WHERE t.transactionId = :tid", nativeQuery = false)
	void updateByTid(@Param("tid") long tid, @Param("prdId") Integer prdId,
			@Param("subId") String subId, @Param("invId") String invId,
			@Param("addl") String addl, @Param("amt") Integer amt,
			@Param("curr") String curr, @Param("txDate") String txDate,
			@Param("txHour") String txHour, @Param("cmAmt") Integer cmAmt,
			@Param("cmCurr") String cmCurr, @Param("type") String type,
			@Param("informed") Boolean informed,
			@Param("billerData") String billerData);
}
