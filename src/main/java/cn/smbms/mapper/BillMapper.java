package cn.smbms.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import cn.smbms.pojo.Bill;
import cn.smbms.pojo.Provider;

public interface BillMapper {
	
	/**
	 * findBillByPage TODO(��ѯ����+��ҳ)
	 * @param map
	 * @return
	 */
	List<Bill> findBillByPage(Map<String,Object> map);

	/**
	 * countByPage TODO(��ѯ����������)
	 * @param map
	 * @return
	 */
	Integer countByPage(Map<String,Object> map);

	/**
	 * delectBill TODO(ͨ��IDɾ��������Ϣ)
	 * @param id
	 * @return
	 */
	Integer delectBill(@Param("id")Integer id);

	/**
	 * addBill TODO(��Ӷ�����Ϣ)
	 * @param bill
	 * @return
	 */
	Integer addBill(Bill bill);

	/**
	 * updateBill TODO(���¶�����Ϣ)
	 * @param bill
	 * @return
	 */
	Integer updateBill(Bill bill);

	/**
	 * findBillByid TODO(ͨ��ID��ȡ������Ϣ)
	 * @param id
	 * @return
	 */
	Bill findBillByid(@Param("id")Integer id);

	/**
	 * showProviderinfo TODO(��ȡ��Ӧ�̶�����Ϣ)
	 * @return
	 */
	List<Provider> showProviderinfo();
}
