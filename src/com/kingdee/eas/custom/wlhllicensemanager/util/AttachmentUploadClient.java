/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/
package com.kingdee.eas.custom.wlhllicensemanager.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.base.attachment.AttachmentException;
import com.kingdee.eas.base.attachment.IAttachmentFtpFacade;
import com.kingdee.eas.base.attachment.ServerConfigHelper;
import com.kingdee.eas.base.attachment.common.AttachmentHelper;
import com.kingdee.eas.base.attachment.ftp.AttachmentFtpInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.util.StringUtils;

public class AttachmentUploadClient {
	Logger logger = Logger.getLogger(AttachmentUploadClient.class);

	IAttachmentFtpFacade iAttachmentFacade = null;

	public AttachmentUploadClient(IAttachmentFtpFacade attachmentFacade) {
		this.iAttachmentFacade = attachmentFacade;
	}

	public String uploadFile4Client(byte[] content) throws BOSException, EASBizException {
		return uploadFile4Client(new ByteArrayInputStream(content));
	}

	private void checkInterface() throws BOSException {
		if (this.iAttachmentFacade != null)
			return;
		this.logger.info("iAttachmentFacade is null, iAttachmentFacade must be set !");
		throw new BOSException("iAttachmentFacade is null!");
	}

	public String uploadFile4Client(InputStream input) throws BOSException, EASBizException {
		return uploadFile4Client(null, input);
	}

	public String uploadFile4Client(String lockFilePath, InputStream input) throws BOSException, EASBizException {
		return uploadFile4Client(null, lockFilePath, input);
	}

	public String uploadFile4Client(String path, String lockFilePath, InputStream input) throws BOSException, EASBizException {
		String tempFileName = path;
		try {
			this.logger.info("......... begin deal upload file, max file size :" + AttachmentFtpInfo.MAX_OF_UPLOAD_LENGTH);
			if (input == null)
				return null;
			AttachmentFtpInfo ftpInfo = null;
			boolean isInit = false;
			if (StringUtils.isEmpty(lockFilePath))
				lockFilePath = System.getProperty("user.home") + File.separator + "canuploadfile.loc";
			File fw = null;
			while (true) {
				fw = new File(lockFilePath);
				if (fw.exists()) {
					fw.delete();
					throw new BOSException("stop upload");
				}

				if (!(isInit)) {
					AttachmentFtpInfo.init();
					checkInterface();
					isInit = true;
				}
				byte[] bf = null;
				if (input.available() > AttachmentFtpInfo.MAX_OF_UPLOAD_LENGTH) {
					bf = new byte[AttachmentFtpInfo.MAX_OF_UPLOAD_LENGTH];
				} else {
					bf = new byte[input.available()];
				}

				int length = input.read(bf, 0, bf.length);
				this.logger.info("....... begin upload file ,size is:" + length + " fileName is:" + StringUtils.cnulls(tempFileName));
				if (length == 0)
					break;
				ftpInfo = this.iAttachmentFacade.uploadFile(tempFileName, bf);
				if ((ftpInfo != null) && (((tempFileName == null) || (tempFileName.trim().length() == 0)))) {
					tempFileName = ftpInfo.getTempFileName();
				}
				if (length < AttachmentFtpInfo.MAX_OF_UPLOAD_LENGTH)
					break;
			}
		} catch (IOException e) {
			this.logger.info(e);
			throw new BOSException(e.getMessage());
		}
		return tempFileName;
	}

	public String uploadFile4Client(String path, String lockPath, byte[] content) throws EASBizException, BOSException {
		return uploadFile4Client(path, null, new ByteArrayInputStream(content));
	}

	public void uploadFile(String attachmentId, byte[] content) throws BOSException, EASBizException {
		uploadFile(null,attachmentId,content) ;
	}
	public void uploadFile(Context ctx,String attachmentId, byte[] content) throws BOSException, EASBizException {
		if ((StringUtils.equalsIgnoreCase("0", AttachmentHelper.getParam(ctx))) && (content.length > 52428800)) {
			throw new AttachmentException(AttachmentException.ATTACHOTHERSTORE);
		}

		if (StringUtils.equalsIgnoreCase("2", AttachmentHelper.getParam(ctx))) {
			String path = ServerConfigHelper.getEASServerPath(ctx, attachmentId);
			uploadFile4Client(path, null, content);
			ServerConfigHelper.saveServerPath(ctx, attachmentId, path);
		} else {
			String fileName = uploadFile4Client(content);
			checkInterface();
			this.iAttachmentFacade.fileToDb4Server(fileName, attachmentId);
		}
	}

	public void uploadFile(String attachmentId, InputStream input) throws BOSException, EASBizException {
		try {
			byte[] filesize = new byte[input.available()];
			input.read(filesize);
			uploadFile(attachmentId, filesize);
		} catch (IOException e) {
			this.logger.info(e);
		}
	}

	public void setIAttachmentFacade(IAttachmentFtpFacade attachmentFacade) {
		this.iAttachmentFacade = attachmentFacade;
	}

	public void uploadFile(String attachmentId, String fileUrl) throws BOSException, EASBizException {
		if (StringUtils.equalsIgnoreCase("0", AttachmentHelper.getParam(null))) {
			File file = new File(fileUrl);
			if (file.length() > 52428800L) {
				throw new AttachmentException(AttachmentException.ATTACHOTHERSTORE);
			}
		}
		if (StringUtils.equalsIgnoreCase("2", AttachmentHelper.getParam(null))) {
			String path = ServerConfigHelper.getEASServerPath(null, attachmentId);
			uploadFile4Client(path, null, fileUrl);
			ServerConfigHelper.saveServerPath(null, attachmentId, path);
		} else {
			String fileName = uploadFile4Client(null, null, fileUrl);
			this.iAttachmentFacade.fileToDb4Server(fileName, attachmentId);
		}
	}

	public String uploadFile4Client(String serverFilePath, String lockFilePath, String localFilePath) throws BOSException, EASBizException {
		String tempFileName = serverFilePath;
		BufferedInputStream input = null;
		try {
			this.logger.info("......... begin deal upload file, max file size :" + AttachmentFtpInfo.MAX_OF_UPLOAD_LENGTH);
			if (localFilePath == null) {
				return null;
			}
			AttachmentFtpInfo ftpInfo = null;

			if (StringUtils.isEmpty(lockFilePath))
				lockFilePath = System.getProperty("user.home") + File.separator + "canuploadfile.loc";
			File fw = new File(lockFilePath);
			if (fw.exists()) {
				fw.delete();
				throw new BOSException("stop upload");
			}

			AttachmentFtpInfo.init();
			checkInterface();
			input = new BufferedInputStream(new FileInputStream(localFilePath));
			int position = 0;
			int readSize = AttachmentFtpInfo.MAX_OF_UPLOAD_LENGTH;
			int totalSize = input.available();
			readSize = (readSize < totalSize) ? readSize : totalSize;

			while (position < totalSize) {
				byte[] bf = new byte[readSize];
				input.read(bf, 0, readSize);
				ftpInfo = this.iAttachmentFacade.uploadFile(tempFileName, bf);
				if ((ftpInfo != null) && (((tempFileName == null) || (tempFileName.trim().length() == 0)))) {
					tempFileName = ftpInfo.getTempFileName();
				}

				position += readSize;
				readSize = (totalSize - position < readSize) ? totalSize - position : readSize;
			}
		} catch (IOException e) {
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					this.logger.error(e);
				}
			}
		}
		return tempFileName;
	}
}