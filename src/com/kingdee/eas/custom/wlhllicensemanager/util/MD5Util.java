package com.kingdee.eas.custom.wlhllicensemanager.util;
/*     */ 
/*     */ import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/*     */ 
/*     */ public class MD5Util
/*     */ {
/*  17 */   protected static char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
/*     */
/*  19 */   protected static MessageDigest messagedigest = null;
/*     */   static
/*     */   {
/*     */     try
/*     */     {
/*  24 */       messagedigest = MessageDigest.getInstance("MD5");
/*     */     }
/*     */     catch (NoSuchAlgorithmException nsaex)
/*     */     {
/*  28 */       System.err.println(MD5Util.class.getName() + "初始化失败，MessageDigest不支持MD5Util。");
/*  29 */       nsaex.printStackTrace();
/*     */     }
/*     */   }
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */   public static String getMD5String(String s)
/*     */   {
/*  41 */     return getMD5String(s.getBytes());
/*     */   }
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */   public static boolean checkPassword(String password, String md5PwdStr)
/*     */   {
/*  53 */     String s = getMD5String(password);
/*  54 */     return s.equals(md5PwdStr);
/*     */   }
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */   public static String getFileMD5String(File file)
/*     */     throws IOException
/*     */   {
/*  67 */     InputStream fis = new FileInputStream(file);
/*  68 */     byte[] buffer = new byte[1024];
/*  69 */     int numRead = 0;
/*  70 */     while ((numRead = fis.read(buffer)) > 0)
/*     */     {
/*  72 */       messagedigest.update(buffer, 0, numRead);
/*     */     }
/*  74 */     fis.close();
/*  75 */     return bufferToHex(messagedigest.digest());
/*     */   }
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */   public static String getFileMD5String_old(File file)
/*     */     throws IOException
/*     */   {
/*  92 */     FileInputStream in = new FileInputStream(file);
/*  93 */     FileChannel ch = in.getChannel();
/*  94 */     MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, 0L, file.length());
/*  95 */     messagedigest.update(byteBuffer);
/*  96 */     return bufferToHex(messagedigest.digest());
/*     */   }
/*     */
/*     */   public static String getMD5String(byte[] bytes)
/*     */   {
/* 101 */     messagedigest.update(bytes);
/* 102 */     return bufferToHex(messagedigest.digest());
/*     */   }
/*     */
/*     */   private static String bufferToHex(byte[] bytes)
/*     */   {
/* 107 */     return bufferToHex(bytes, 0, bytes.length);
/*     */   }
/*     */
/*     */   private static String bufferToHex(byte[] bytes, int m, int n)
/*     */   {
/* 112 */     StringBuffer stringbuffer = new StringBuffer(2 * n);
/* 113 */     int k = m + n;
/* 114 */     for (int l = m; l < k; ++l)
/*     */     {
/* 116 */       appendHexPair(bytes[l], stringbuffer);
/*     */     }
/* 118 */     return stringbuffer.toString();
/*     */   }
/*     */
/*     */   private static void appendHexPair(byte bt, StringBuffer stringbuffer)
/*     */   {
/* 123 */     char c0 = hexDigits[((bt & 0xF0) >> 4)];
/* 124 */     char c1 = hexDigits[(bt & 0xF)];
/* 125 */     stringbuffer.append(c0);
/* 126 */     stringbuffer.append(c1);
/*     */   }
/*     */
/*     */   public static void main(String[] args) throws IOException
/*     */   {
/* 131 */     long begin = System.currentTimeMillis();
/*     */ 
/* 133 */     File file = new File("C:/12345.txt");
/* 134 */     String md5 = getFileMD5String(file);
/*     */
/*     */
/*     */ 
/* 138 */     long end = System.currentTimeMillis();
/* 139 */     System.out.println("md5:" + md5 + " time:" + ((end - begin) / 1000L) + "s");
/*     */   }
/*     */ }

