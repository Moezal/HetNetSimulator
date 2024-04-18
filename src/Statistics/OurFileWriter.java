/*
 * Copyright (C) 2015 mohamad
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package Statistics;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mohamad
 */
public class OurFileWriter {

    public OurFileWriter(String content, String location, boolean append) {
        if (append) {

            File f = new File(location);

            if (!f.exists()) {
                f.getParentFile().mkdirs();
                PrintWriter printWriter;
                try {
                    printWriter = new PrintWriter(f);
                    printWriter.println(content);
                    printWriter.flush();
                    printWriter.close();
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(OurFileWriter.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else {

                try {
                    content=content.replace("legend", "legappend");
                    FileWriter fw = new FileWriter(location,true);
                    fw.write(content);
                    fw.close();

                } catch (IOException ex) {
                    Logger.getLogger(OurFileWriter.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            File file = new File(location);
            file.getParentFile().mkdirs();

            try {
                PrintWriter printWriter = new PrintWriter(file);
                printWriter.println(content);
                printWriter.flush();
                printWriter.close();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(OurFileWriter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
