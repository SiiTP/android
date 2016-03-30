package com.dbtest.ivan.app.logic.db;

import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

/**                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     q
 * Created by ivan on 30.03.16.
 */
public class OrmConfigUtils extends OrmLiteConfigUtil {

    public static void main(String[] args) throws IOException, SQLException {
        System.out.println("Project dir " + System.getProperty("user.dir"));
        File f = new File("src/main/res/raw/ormlite_config.txt");
        System.out.println("File locationt " + f.getAbsolutePath());
        writeConfigFile(f);
    }
}
