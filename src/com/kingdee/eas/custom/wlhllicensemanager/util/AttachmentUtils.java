package com.kingdee.eas.custom.wlhllicensemanager.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.sql.Blob;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import sun.misc.BASE64Decoder;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.actiondispatcher.DispatcherContext;
import com.kingdee.bos.dao.ormapping.ObjectStringPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.attachment.AttachmentCollection;
import com.kingdee.eas.base.attachment.AttachmentFactory;
import com.kingdee.eas.base.attachment.AttachmentFtpFacadeFactory;
import com.kingdee.eas.base.attachment.AttachmentInfo;
import com.kingdee.eas.base.attachment.BoAttchAssoCollection;
import com.kingdee.eas.base.attachment.BoAttchAssoFactory;
import com.kingdee.eas.base.attachment.BoAttchAssoInfo;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.base.attachment.common.AttachmentServerManager;
import com.kingdee.eas.base.attachment.common.SimpleAttachmentInfo;
import com.kingdee.eas.base.attachment.ftp.AttachmentUploadClient;
import com.kingdee.eas.base.attachment.util.VariousAttachmentInfoMaker;
import com.kingdee.eas.base.fme.service.FMEServiceFactory;
import com.kingdee.eas.base.fme.service.IFMEService;
import com.kingdee.eas.base.fme.service.ServiceResult;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.StringUtils;

public class AttachmentUtils {

	private Logger logger = Logger.getLogger(AttachmentUtils.class);
	private Context ctx;

	public AttachmentUtils(Context ctx) {
		this.ctx = ctx;
	}

	public Map addAttachment(String boid, Map m, String fileData) throws EASBizException, BOSException, IOException {
		SimpleAttachmentInfo sai = new SimpleAttachmentInfo();
		boid = URLDecoder.decode(boid);
		String type = (String) m.get("type");
		String attatchid = (String) m.get("attatchid");
		String fileSize = (String) m.get("fileSize");
		String fileName = (String) m.get("fileName");
		String fileExt = (String) m.get("fileExt");
		Map resultMap = new HashMap();
		fileName = URLDecoder.decode(fileName);
		byte[] content = null;
//		System.out.println("******附件上传70****");
		if ("getAttach".equals(type)) {
			content = getAttachByte(attatchid);
		} else if ("getPicthur".equals(type)) {
			BASE64Decoder decoder = new BASE64Decoder();
			content = decoder.decodeBuffer(fileData);
		}
//		System.out.println("******附件上传77****");
		sai.setContent(content);
		sai.setExtName(fileExt);
		sai.setBeizhu("动态接口附件上传");
		sai.setMainName(fileName);
//		System.out.println("******附件上传82****");
		AttachmentInfo attachmentInfo = VariousAttachmentInfoMaker.makeAppAttachmentInfo(this.ctx, AttachmentFactory.getLocalInstance(this.ctx), boid, sai);
//		System.out.println("******附件上传84****");
		String attchID = "";
		byte[] fileInfo = attachmentInfo.getFile();
//		System.out.println("******附件上传87****"+fileInfo.length);
		if ((fileInfo != null) && (fileInfo.length > 5242880)) {
//			System.out.println("******附件上传88****");
			attachmentInfo.setFile(null);
			attchID = AttachmentFactory.getLocalInstance(this.ctx).addnew(attachmentInfo).toString();
			try {
//				IAttachmentFtpFacade iAttachmentFacade = AttachmentFtpFacadeFactory.getLocalInstance(this.ctx);
				AttachmentUploadClient fileUpload = new AttachmentUploadClient(AttachmentFtpFacadeFactory.getLocalInstance(this.ctx));
//				System.out.println("******附件上传95****");
				fileUpload.uploadFile(attchID, fileInfo);
//				System.out.println("******附件上传97****");
			} catch (BOSException e) {
//				System.out.println("******附件上传99****");
				AttachmentFactory.getLocalInstance(this.ctx).delete(new ObjectStringPK(attchID));
				this.logger.info("upload file fail:", e);
				throw e;
			} catch (EASBizException e) {
//				System.out.println("******附件上传104****");
				AttachmentFactory.getLocalInstance(this.ctx).delete(new ObjectStringPK(attchID));
				this.logger.info("upload file fail:", e);
				throw e;
			} catch (Exception e) {
//				System.out.println("******附件上传109****");
				AttachmentFactory.getLocalInstance(this.ctx).delete(new ObjectStringPK(attchID));
				this.logger.info("upload file fail:", e);
				throw new BOSException(e.getMessage());
			}
		} else {
//			System.out.println("******附件上传114***");
			this.logger.info(" begin one part file upload");
			attchID = AttachmentFactory.getLocalInstance(this.ctx).addnew(attachmentInfo).toString();
		}
		resultMap.put("success", Boolean.valueOf(true));
//		System.out.println("******附件上传119****");
		return resultMap;
	}

	private byte[] getAttachByte(String attatchid) {
		String root = "http://yunzhijia.com/microblog/filesvr/" + attatchid;
		URL url = null;

		byte[] data = null;
		try {
			url = new URL(root);
			InputStream in = url.openStream();
			ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
			byte[] buff = new byte[100];
			int rc = 0;
			while ((rc = in.read(buff, 0, 100)) > 0) {
				swapStream.write(buff, 0, rc);
			}
			data = swapStream.toByteArray();
			System.out.println("文件读完");
		} catch (Exception e) {
			System.out.println("IO异常：" + e);
		}
		byte[] content = data;
		for (int i = 0; i < content.length; ++i) {
			if (content[i] < 0) {/*      */
				int tmp152_150 = i;/*      */
				byte[] tmp152_148 = content;
				tmp152_148[tmp152_150] = (byte) (tmp152_148[tmp152_150] + 256);
			}
		}
		return content;
	}

	public static byte[] toByteArray(InputStream in) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] buffer = new byte[4096];
		int n = 0;
		while ((n = in.read(buffer)) != -1) {
			out.write(buffer, 0, n);
		}
		return out.toByteArray();
	}

	public Map deleteAttachment(String billId, String id) throws EASBizException, BOSException {
		Map m = new HashMap();
		try {
			BoAttchAssoInfo binfo = new BoAttchAssoInfo();
			binfo.setBoID(billId);
			AttachmentInfo attachmentInfo = new AttachmentInfo();
			attachmentInfo.setId(BOSUuid.read(id));
			binfo.setAttachment(attachmentInfo);
			BoAttchAssoCollection bc = new BoAttchAssoCollection();
			bc.add(binfo);
			BoAttchAssoFactory.getLocalInstance(this.ctx).deleteByBoList(bc);
			m.put("success", Boolean.valueOf(true));
		} catch (Exception e) {
			m.put("success", Boolean.valueOf(false));
			m.put("canAdd", Boolean.valueOf(false));
		}
		return m;
	}

	public String checkID(String bosType) {
		if (bosType == null) {
			return null;
		}
		return bosType.replaceAll(" ", "+");
	}

	public String getAttachment(String id, String type) throws EASBizException, BOSException, UnsupportedEncodingException {
		long t1 = System.currentTimeMillis();

		File file = null;
		if (!(StringUtils.isEmpty(id))) {
			id = checkID(id);
			AttachmentInfo info = null;
			FileOutputStream out = null;
			String fileName = "";
			String simpleName = "";
			byte[] size = null;
			try {
				if ("BILLATTACH".equals(type)) {
					String sql = "select FileData from T_BAS_BillAttachment where FID ='" + id + "'";

					IRowSet countRowSet = null;
					countRowSet = DbUtil.executeQuery(this.ctx, sql);
					if (countRowSet.size() > 0) {
						while (countRowSet.next()) {
							Blob sx = null;
							sx = countRowSet.getBlob(1);
							size = sx.getBytes(1L, (int) sx.length());
						}

					} else {
						sql = "select fdocentity from T_CP_Document where FID ='" + id + "'";
						countRowSet = DbUtil.executeQuery(this.ctx, sql);
						if (countRowSet.size() > 0) {
							while (countRowSet.next()) {
								Blob sx = null;
								sx = countRowSet.getBlob(1);
								size = sx.getBytes(1L, (int) sx.length());
							}
						} else {
							sql = "select FDocEntity from T_CP_DocReceive where FID ='" + id + "'";

							countRowSet = DbUtil.executeQuery(this.ctx, sql);
							if (countRowSet.size() > 0) {
								while (countRowSet.next()) {
									Blob sx = null;
									sx = countRowSet.getBlob(1);
									size = sx.getBytes(1L, (int) sx.length());
								}
							} else {
								sql = "select FDocEntity from T_CP_DocDispatch where FID ='" + id + "'";
								countRowSet = DbUtil.executeQuery(this.ctx, sql);
								if (countRowSet.size() > 0) {
									while (countRowSet.next()) {
										Blob sx = null;
										sx = countRowSet.getBlob(1);
										size = sx.getBytes(1L, (int) sx.length());
									}
								} else {
									sql = "select FContentFile  from T_CON_ContractContent where FID ='" + id + "'";
									countRowSet = DbUtil.executeQuery(this.ctx, sql);
									if (countRowSet.size() > 0) {
										while (countRowSet.next()) {
											Blob sx = null;
											sx = countRowSet.getBlob(1);
											size = sx.getBytes(1L, (int) sx.length());
										}
									}
								}
							}
						}
					}

					simpleName = "doc";
					fileName = "正文";
					// break label1043;
				}
				if ("FTP".equals(type)) {
					IFMEService fmeService = FMEServiceFactory.getServiceInstance(this.ctx);
					HttpServletRequest request = DispatcherContext.getHttpServletRequest();
					ServiceResult re = fmeService.downloadFileInServer(id, request);
					String path1 = re.getResultDetail();
					return path1;

				}

				if (!(StringUtils.isEmpty(id))) {
					try {
						BOSUuid.read(id);
						info = AttachmentFactory.getLocalInstance(this.ctx).getAttachmentInfo(new ObjectUuidPK(id));
						if (info == null) {
							AttachmentCollection col = AttachmentFactory.getLocalInstance(this.ctx).getAttachmentCollection("where attachID = '" + id + "'");
							if ((col != null) && (col.size() > 0)) {
								info = col.get(0);
							}
						}
					} catch (Exception e) {
						info = AttachmentFactory.getLocalInstance(this.ctx).getAttachmentInfo("where attachID = '" + id + "'");
					}
					AttachmentServerManager asm = AttachmentManagerFactory.getServerManager(this.ctx);
					size = asm.download(info.getId().toString());
					simpleName = info.getSimpleName();
					fileName = info.getName();
				}

				String path = System.getProperty("EAS_HOME");
				path = path + "/server/deploy/eas.ear/light_app_bas.war/download/";
				file = new File(path);
				if (!(file.exists())) {
					file.mkdirs();
				}
				file = new File(path + fileName + "." + simpleName);
				Random rom = new Random();
				while (file.exists()) {
					file = new File(path + fileName + "." + rom.nextInt(1000) + "." + simpleName);

				}

				out = new FileOutputStream(file);
				out.write(size);
				// label1043: out.flush();

			} catch (Exception e) {
				this.logger.error("lightapp getAttachMent error :: " + e.getMessage(), e);
			} finally {
				try {
					out.close();
				} catch (Exception ex) {
					this.logger.error(ex);
				}
			}
		}

		this.logger.debug("getAttachMent timeout :: " + (System.currentTimeMillis() - t1));
		return file.getName();
	}

	public byte[] getAttachmentAsBytes(String id, String type) throws EASBizException, BOSException, UnsupportedEncodingException {
		long t1 = System.currentTimeMillis();
		System.out.print("id=================================================" + id);
		System.out.print("type=================================================" + type);
		File file = null;
		if (!(StringUtils.isEmpty(id))) {
			id = checkID(id);
			AttachmentInfo info = null;
			FileOutputStream out = null;
			String fileName = "";
			String simpleName = "";
			byte[] size = null;
			try {
				if ("BILLATTACH".equals(type)) {
					this.logger.error("lightapp getAttachmentAsBytes  :: type" + type);
					String sql = "select FileData from T_BAS_BillAttachment where FID ='" + id + "'";

					IRowSet countRowSet = null;
					countRowSet = DbUtil.executeQuery(this.ctx, sql);
					this.logger.error("lightapp getAttachmentAsBytes  :: countRowSet.size() =" + countRowSet.size());
					if (countRowSet.size() > 0) {
						while (countRowSet.next()) {
							Blob sx = null;
							sx = countRowSet.getBlob(1);
							size = sx.getBytes(1L, (int) sx.length());
						}

					} else {
						sql = "ct FContent from T_CW_CooperativeWorkBill where fid='" + id + "'";
						countRowSet = DbUtil.executeQuery(this.ctx, sql);
						this.logger.error("lightapp getAttachmentAsBytes  :: countRowSet.size() =" + countRowSet.size());
						if (countRowSet.size() > 0) {
							while (countRowSet.next()) {
								Blob sx = null;
								sx = countRowSet.getBlob(1);
								size = sx.getBytes(1L, (int) sx.length());
							}
						} else {
							sql = "select FDocEntity from T_CP_DocReceive where FID ='" + id + "'";

							countRowSet = DbUtil.executeQuery(this.ctx, sql);
							this.logger.error("lightapp getAttachmentAsBytes  :: countRowSet.size() =" + countRowSet.size());
							if (countRowSet.size() > 0) {
								while (countRowSet.next()) {
									Blob sx = null;
									sx = countRowSet.getBlob(1);
									size = sx.getBytes(1L, (int) sx.length());
								}
							} else {
								sql = "select fdocentity from T_CP_Document where FID ='" + id + "'";
								countRowSet = DbUtil.executeQuery(this.ctx, sql);
								this.logger.error("lightapp getAttachmentAsBytes  :: countRowSet.size() =" + countRowSet.size());
								if (countRowSet.size() > 0) {
									while (countRowSet.next()) {
										Blob sx = null;
										sx = countRowSet.getBlob(1);
										size = sx.getBytes(1L, (int) sx.length());
									}

								} else {
									sql = "select FContentfile  from T_CON_ContractContent WHERE FID = '" + id + "'";
									System.out.print("sql=================================================" + sql);
									countRowSet = DbUtil.executeQuery(this.ctx, sql);
									System.out.print("type=================================================" + countRowSet.size());
									if (countRowSet.size() > 0) {
										while (countRowSet.next()) {
											Blob sx = null;
											sx = countRowSet.getBlob(1);
											size = sx.getBytes(1L, (int) sx.length());
										}
									}
								}
							}
						}
					}

					simpleName = "doc";
					fileName = "正文";
				} else {
					if ("FTP".equals(type)) {
						IFMEService fmeService = FMEServiceFactory.getServiceInstance(this.ctx);
						HttpServletRequest request = DispatcherContext.getHttpServletRequest();

						String sql = "select fbusinessid,fdocumentid from t_fme_busidoc  where fid='" + id + "'";

						IRowSet rows = DbUtil.executeQuery(this.ctx, sql);
						String docuid = "";
						String busid = "";
						while (rows.next()) {
							docuid = rows.getString("fdocumentid");
							busid = rows.getString("fbusinessid");
						}
						ServiceResult re = fmeService.downloadFileInServer(busid, docuid, request);
						String path1 = re.getResultDetail();
						File file1 = new File(path1);
						FileInputStream fis = new FileInputStream(file1);
						ByteArrayOutputStream bos = new ByteArrayOutputStream();
						byte[] b = new byte[1024];
						int n;
						while ((n = fis.read(b)) != -1) {
							bos.write(b, 0, n);
						}
						fis.close();
						bos.close();
						size = bos.toByteArray();
						String[] s = new String[1];
						s[0] = path1;
						file1.delete();
						return size;

					}

					try {
						BOSUuid.read(id);
						info = AttachmentFactory.getLocalInstance(this.ctx).getAttachmentInfo(new ObjectUuidPK(id));
						if (info == null) {
							AttachmentCollection col = AttachmentFactory.getLocalInstance(this.ctx).getAttachmentCollection("where attachID = '" + id + "'");
							if ((col != null) && (col.size() > 0)) {
								info = col.get(0);
							}
						}
					} catch (Exception e) {
						info = AttachmentFactory.getLocalInstance(this.ctx).getAttachmentInfo("where attachID = '" + id + "'");
					}
					AttachmentServerManager asm = AttachmentManagerFactory.getServerManager(this.ctx);
					size = asm.download(info.getId().toString());
					this.logger.error("lightapp getAttachmentAsBytes  ::size.size() =" + size.length);
					simpleName = info.getSimpleName();
					fileName = info.getName();

				}

				return size;
			} catch (Exception e) {
				this.logger.error("lightapp getAttachMent error :: " + e.getMessage(), e);
			}

		}

		return null;
	}
}
