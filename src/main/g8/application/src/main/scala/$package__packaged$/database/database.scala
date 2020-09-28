package $package$

import $package$.database.util.{
  CirceJsonbMeta,
  Filterables
}

package object database
    extends CirceJsonbMeta
    with Filterables
