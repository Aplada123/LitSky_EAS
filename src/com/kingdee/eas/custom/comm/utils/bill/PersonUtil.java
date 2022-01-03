package com.kingdee.eas.custom.comm.utils.bill;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.AdminOrgUnitCollection;
import com.kingdee.eas.basedata.org.AdminOrgUnitFactory;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgUnitCollection;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgUnitRelationFactory;
import com.kingdee.eas.basedata.org.PositionFactory;
import com.kingdee.eas.basedata.org.PositionHierarchyCollection;
import com.kingdee.eas.basedata.org.PositionHierarchyFactory;
import com.kingdee.eas.basedata.org.PositionHierarchyInfo;
import com.kingdee.eas.basedata.org.PositionInfo;
import com.kingdee.eas.basedata.org.PositionMemberFactory;
import com.kingdee.eas.basedata.org.PositionMemberInfo;
import com.kingdee.eas.basedata.person.PersonFactory;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.basedata.person.client.PersonPromptBox;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.hr.emp.PersonPositionFactory;
import com.kingdee.eas.hr.emp.PersonPositionInfo;

public class PersonUtil {
	static Logger logger = Logger.getLogger(PersonUtil.class.getName());
	/**
	 * 根据员工的info或者id获取只要职位
	 * @param obj
	 * @return
	 */
	public static PositionInfo getPersonPositionByPerson(Context ctx,Object obj){

		String PersonId="";
		if(obj==null){
			System.out.println("输入为空");
			return null;
		}
		if(obj instanceof String)
			PersonId=(String)obj;
		if(obj instanceof PersonInfo)
			PersonId=((PersonInfo)obj).getId().toString();

		try {
			CoreBaseCollection coll;
			if(ctx==null)
				coll = PositionMemberFactory.getRemoteInstance().getCollection("where person='"+PersonId+"' and IsPrimary=1");
			else
				coll = PositionMemberFactory.getLocalInstance(ctx).getCollection("where person='"+PersonId+"' and IsPrimary=1");
			if(coll.size()>1){
				System.out.println("存在历史记录\\(≧▽≦)/");
				return null;
			}

			if(coll.size()==1){
				PositionMemberInfo info=(PositionMemberInfo) coll.get(0);
				if(info.getPosition().getId()!=null){
					if(ctx==null)
						return PositionFactory.getRemoteInstance().getPositionInfo(new ObjectUuidPK(info.getPosition().getId().toString()));
					else
						return PositionFactory.getLocalInstance(ctx).getPositionInfo(new ObjectUuidPK(info.getPosition().getId().toString()));
				}
			}
		} catch (BOSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EASBizException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获得某人员的直接上级组织
	 */
	public static PositionInfo getDirectUpperPositionByPerson(Context ctx,Object obj){
		PositionInfo childPosition = getPersonPositionByPerson(ctx, obj);
		if(childPosition==null)
			return null;
		PositionHierarchyCollection coll;
		try {
			if(ctx==null)
				coll=PositionHierarchyFactory.getRemoteInstance().getPositionHierarchyCollection("where child='"+childPosition.getId().toString()+"'");
			else
				coll=PositionHierarchyFactory.getLocalInstance(ctx).getPositionHierarchyCollection("where child='"+childPosition.getId().toString()+"'");
			if(coll.size()>0){
				String positionID = ((PositionHierarchyInfo)coll.get(0)).getParent().getId().toString();
				if(ctx==null)
					return PositionFactory.getRemoteInstance().getPositionInfo(new ObjectUuidPK(positionID));
				else
					PositionFactory.getLocalInstance(ctx).getPositionInfo(new ObjectUuidPK(positionID));
			}
		} catch (BOSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EASBizException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 获取员工挂靠的部门
	 * @param information
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 */
	public static AdminOrgUnitInfo getAdminOrgUnit(Object information) throws BOSException, EASBizException {
		if (information == null || information.toString().equals("")) {
			return null;
		}

		PersonInfo person = null;

		if (information instanceof PersonInfo)
			person = (PersonInfo)information; 
		if (information instanceof String) {
			CoreBaseCollection colls = PersonFactory.getRemoteInstance().getCollection(" where id='" + (String)information + "' or number='" + (String)information + "'");
			if (colls.size() == 0) {
				return null;
			}
			person = (PersonInfo)colls.get(0);
		} 


		AdminOrgUnitInfo adminOrgUnitInfo = null;
		if (person == null)
			return null; 
		CoreBaseCollection coll = PersonPositionFactory.getRemoteInstance().getCollection(" where person='" + person.getId().toString() + "'");
		if (coll.size() <= 0)
			return null; 
		PersonPositionInfo personPosition = (PersonPositionInfo)coll.get(0);
		String id = personPosition.getPersonDep().getId().toString();
		if (!id.equals("") && id != null)
			adminOrgUnitInfo = AdminOrgUnitFactory.getRemoteInstance().getAdminOrgUnitInfo((IObjectPK)new ObjectUuidPK(id)); 
		return adminOrgUnitInfo;
	}

	/**
	 * 注册人员信息
	 * @param bizBoxProposer
	 * @param coreui
	 * @param isSingle
	 */
	public static void makeApplierF7(KDBizPromptBox bizBoxProposer, String FIOrgUnitID,CoreUIObject coreui, boolean isSingle)
	{
		OrgUnitCollection unitColl = null;
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		FilterItemCollection fic = filter.getFilterItems();
		try
		{
			unitColl = OrgUnitRelationFactory.getRemoteInstance().getFromUnit(FIOrgUnitID, 1, 0);
			int size = 0;
			if(unitColl != null)
				size = unitColl.size();
			fic.add(new FilterItemInfo("EmployeeType.inService", new Integer(2), CompareType.NOTEQUALS));
			if(size > 0)
			{
				Set<String> idSet = new HashSet<String>();
				for(int i = 0; i < size; i++)
					idSet.add(unitColl.get(i).getId().toString());

				fic.add(new FilterItemInfo("AdminOrgUnit.id", idSet, CompareType.INCLUDE));
				filter.getFilterItems().add(new FilterItemInfo("AdminOrgUnit.id", idSet, CompareType.INCLUDE));
			}
			evi.setFilter(filter);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		PersonPromptBox selector = new PersonPromptBox(coreui);
		selector.setIsSingleSelect(isSingle);
		bizBoxProposer.setEntityViewInfo(evi);
		bizBoxProposer.setSelector(selector);
		bizBoxProposer.getQueryAgent().setHasCUDefaultFilter(false);
		bizBoxProposer.setDisplayFormat("$name$");
		bizBoxProposer.setEditFormat("$name$");
		bizBoxProposer.setCommitFormat("$name$");
		bizBoxProposer.setDefaultF7UIName("人员选择");
		bizBoxProposer.setQueryInfo("com.kingdee.eas.cp.bc.app.PersonQuery");
	}

	public static void makeApplierF7ByOrgUnit(KDBizPromptBox bizPromptBox, OrgUnitInfo orgUnitInfo) {
		makeApplierF7ByOrgUnit(bizPromptBox, orgUnitInfo, false);
	}

	public static void makeApplierF7ByOrgUnit(KDBizPromptBox bizPromptBox, OrgUnitInfo orgUnitInfo, boolean isLoadFieldAction){
		if (orgUnitInfo == null) {
			makeApplierF7(bizPromptBox, null);
		} else {
			String orgUnitLongNumber = orgUnitInfo.getLongNumber();
			String orgUnitId = orgUnitInfo.getId().toString();
			//			OrgUnitCollection unitColl = null;
			EntityViewInfo evi = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			FilterItemCollection fic = filter.getFilterItems();

			fic.add(new FilterItemInfo("EmployeeType.inService", new Integer(2), CompareType.NOTEQUALS));
			fic.add(new FilterItemInfo("AdminOrgUnit.longNumber", orgUnitLongNumber + "%", CompareType.LIKE));
			evi.setFilter(filter);

			PersonPromptBox selector = new PersonPromptBox((CoreUIObject)null);
			selector.setIsSingleSelect(true);
			bizPromptBox.setEntityViewInfo(evi);
			bizPromptBox.setSelector(selector);
			bizPromptBox.getQueryAgent().setHasCUDefaultFilter(false);
			bizPromptBox.setDisplayFormat("$name$");
			bizPromptBox.setEditFormat("$number$");
			bizPromptBox.setCommitFormat("$number$");
			bizPromptBox.setQueryInfo("com.kingdee.eas.cp.bc.app.PersonQuery");
			if ((isLoadFieldAction) || (bizPromptBox.getData() == null) || (((PersonInfo)bizPromptBox.getData()).getId() == null))
				return;
			String personId = ((PersonInfo)bizPromptBox.getData()).getId().toString();
			if (!(isPersonInOrgUnit(personId, orgUnitId))){
				bizPromptBox.setValue(null);
			}
		}
	}
	public static void makeApplierF7(KDBizPromptBox bizBoxProposer, CoreUIObject coreui) {
		makeApplierF7(bizBoxProposer, coreui, true);
	}

	public static void makeApplierF7(KDBizPromptBox bizBoxProposer, CoreUIObject coreui, boolean isSingle)
	{
		OrgUnitCollection unitColl = null;
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		FilterItemCollection fic = filter.getFilterItems();
		fic.add(new FilterItemInfo("EmployeeType.inService", new Integer(2), CompareType.NOTEQUALS));
		try {
			unitColl = OrgUnitRelationFactory.getRemoteInstance().getFromUnit(SysContext.getSysContext().getCurrentFIUnit().getId().toString(), 1, 0);
			List<String> adminOrgIds = new ArrayList<String>();
			int i = 0; for (int is = unitColl.size(); i < is; ++i) {
				adminOrgIds.add(unitColl.get(i).getId().toString());
			}
			if (adminOrgIds.size() == 0)
			{
				adminOrgIds.add("nodata");
			}
			fic.add(new FilterItemInfo("AdminOrgUnit.id", new HashSet<String>(adminOrgIds), CompareType.INCLUDE));
			evi.setFilter(filter);
		} catch (EASBizException e) {
			logger.error(e.getMessage());
		} catch (BOSException e) {
			logger.error(e.getMessage());
		}
		PersonPromptBox selector = new PersonPromptBox();
		selector.setIsSingleSelect(isSingle);
		bizBoxProposer.setSelector(selector);
		bizBoxProposer.setEntityViewInfo(evi);
		bizBoxProposer.getQueryAgent().setHasCUDefaultFilter(false);
		bizBoxProposer.setDisplayFormat("$name$");
		bizBoxProposer.setEditFormat("$number$");
		bizBoxProposer.setCommitFormat("$number$");
		bizBoxProposer.setQueryInfo("com.kingdee.eas.cp.bc.app.PersonQuery"); 
	}
	public static boolean isPersonInOrgUnit(String personId, String orgUnitId) {
		boolean isIn = false;
		if ((personId != null) && (!("".equals(personId))) && (orgUnitId != null) && (!("".equals(orgUnitId)))) {
			try
			{
				AdminOrgUnitCollection orgUnitCol = PersonFactory.getRemoteInstance().getAllAdminOrgUnit(BOSUuid.read(personId));

				AdminOrgUnitInfo adminOrgUnitInfo = null;
				for (int i = orgUnitCol.size() - 1; i >= 0; --i) {
					adminOrgUnitInfo = orgUnitCol.get(i);
					if (orgUnitId.equals(adminOrgUnitInfo.getId().toString())) {
						isIn = true;
						break;
					}
				}
			} catch (EASBizException e) {
				logger.error(e.getMessage());
			} catch (BOSException e) {
				logger.error(e.getMessage());
			}
		}
		return isIn; 
	}
}
