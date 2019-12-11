package org.csu.workmaster.Dao;

import org.csu.workmaster.Entity.File;
public interface FileDao {

    public void saveFile(File file);
    public File findFileByFileId(long fileId);
    public void updateFile(File file);
    public void deleteFileByFileId(long fileId);
    public File findFileByuploaderid(long userid);

}
