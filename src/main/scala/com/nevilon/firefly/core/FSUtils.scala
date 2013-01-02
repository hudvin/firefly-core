package com.nevilon.firefly.core

import java.io.File

/**
 * Created with IntelliJ IDEA.
 * User: hudvin
 * Date: 1/1/13
 * Time: 8:51 AM
 * To change this template use File | Settings | File Templates.
 */
object FSUtils {

  def tree(root: File, skipHidden: Boolean = false): Stream[File] =
    if (!root.exists || (skipHidden && root.isHidden)) Stream.empty
    else root #:: (
      root.listFiles match {
        case null => Stream.empty
        case files => files.toStream.flatMap(tree(_, skipHidden))
      })


  /*
   def findFiles(fileFilter: (File) => Boolean = (f) => true)(f: File): List[File] = {
     val ss = f.list()
     val list = if (ss == null) {
       Nil
     } else {
       ss.toList.sorted
     }
     val visible = list.filter(_.charAt(0) != '.')
     val these = visible.map(new File(f, _))
     these.filter(fileFilter) ++ these.filter(_.isDirectory).flatMap(findFiles(fileFilter))
   }

 */

}
