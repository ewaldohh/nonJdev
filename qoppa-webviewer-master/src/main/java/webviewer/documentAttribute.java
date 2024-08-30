/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webviewer;

import java.io.File;
import java.io.Serializable;

/**
 *
 * @author DonnyAM
 */
class documentAttribute implements Serializable {

    private String aclType;
    private String consolidate;
    private String did;
    private String etdmsServer;
    private String etdmsTempDir;
    private String flag;
    private String ggi;
    private String passWD;
    private String reviewId;
    private String reviewOwner;
    private String ucmServer;
    private String dDocName;
    private String filePath;
    private String fileName;
    private String offlineLoc;
    private String userName;
    private String fileXFDF;
    private String filePDF;
    private String isOffline;
    private File saveFilePDF;
    /**
     * @return the aclType
     */
    public String getAclType() {
        return aclType;
    }

    /**
     * @param aclType the aclType to set
     */
    public void setAclType(String aclType) {
        this.aclType = aclType;
    }

    /**
     * @return the consolidate
     */
    public String getConsolidate() {
        return consolidate;
    }

    /**
     * @param consolidate the consolidate to set
     */
    public void setConsolidate(String consolidate) {
        this.consolidate = consolidate;
    }

    /**
     * @return the did
     */
    public String getDid() {
        return did;
    }

    /**
     * @param did the did to set
     */
    public void setDid(String did) {
        this.did = did;
    }

    /**
     * @return the etdmsServer
     */
    public String getEtdmsServer() {
        return etdmsServer;
    }

    /**
     * @param etdmsServer the etdmsServer to set
     */
    public void setEtdmsServer(String etdmsServer) {
        this.etdmsServer = etdmsServer;
    }

    /**
     * @return the etdmsTempDir
     */
    public String getEtdmsTempDir() {
        return etdmsTempDir;
    }

    /**
     * @param etdmsTempDir the etdmsTempDir to set
     */
    public void setEtdmsTempDir(String etdmsTempDir) {
        this.etdmsTempDir = etdmsTempDir;
    }

    /**
     * @return the flag
     */
    public String getFlag() {
        return flag;
    }

    /**
     * @param flag the flag to set
     */
    public void setFlag(String flag) {
        this.flag = flag;
    }

    /**
     * @return the ggi
     */
    public String getGgi() {
        return ggi;
    }

    /**
     * @param ggi the ggi to set
     */
    public void setGgi(String ggi) {
        this.ggi = ggi;
    }

    /**
     * @return the passWD
     */
    public String getPassWD() {
        return passWD;
    }

    /**
     * @param passWD the passWD to set
     */
    public void setPassWD(String passWD) {
        this.passWD = passWD;
    }

    /**
     * @return the reviewId
     */
    public String getReviewId() {
        return reviewId;
    }

    /**
     * @param reviewId the reviewId to set
     */
    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }

    /**
     * @return the reviewOwner
     */
    public String getReviewOwner() {
        return reviewOwner;
    }

    /**
     * @param reviewOwner the reviewOwner to set
     */
    public void setReviewOwner(String reviewOwner) {
        this.reviewOwner = reviewOwner;
    }

    /**
     * @return the ucmServer
     */
    public String getUcmServer() {
        return ucmServer;
    }

    /**
     * @param ucmServer the ucmServer to set
     */
    public void setUcmServer(String ucmServer) {
        this.ucmServer = ucmServer;
    }

    /**
     * @return the dDocName
     */
    public String getdDocName() {
        return dDocName;
    }

    /**
     * @param dDocName the dDocName to set
     */
    public void setdDocName(String dDocName) {
        this.dDocName = dDocName;
    }

    /**
     * @return the filePath
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * @param filePath the filePath to set
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @param fileName the fileName to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * @return the offlineLoc
     */
    public String getOfflineLoc() {
        return offlineLoc;
    }

    /**
     * @param offlineLoc the offlineLoc to set
     */
    public void setOfflineLoc(String offlineLoc) {
        this.offlineLoc = offlineLoc;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the fileXFDF
     */
    public String getFileXFDF() {
        return fileXFDF;
    }

    /**
     * @param fileXFDF the fileXFDF to set
     */
    public void setFileXFDF(String fileXFDF) {
        this.fileXFDF = fileXFDF;
    }

    /**
     * @return the filePDF
     */
    public String getFilePDF() {
        return filePDF;
    }

    /**
     * @param filePDF the filePDF to set
     */
    public void setFilePDF(String filePDF) {
        this.filePDF = filePDF;
    }

	/**
	 * @return the isOffline
	 */
	public String getIsOffline() {
		return isOffline;
	}

	/**
	 * @param isOffline the isOffline to set
	 */
	public void setIsOffline(String isOffline) {
		this.isOffline = isOffline;
	}

    /**
     * @return the saveFilePDF
     */
    public File getSaveFilePDF() {
        return saveFilePDF;
    }

    /**
     * @param saveFilePDF the saveFilePDF to set
     */
    public void setSaveFilePDF(File saveFilePDF) {
        this.saveFilePDF = saveFilePDF;
    }
}
