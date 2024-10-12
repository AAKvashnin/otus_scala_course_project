package ru.otus.icebergcatalog

import zio.Has


package object db {

  type DataSource = Has[javax.sql.DataSource]



}
