package $package$

import $package$.database.util.{
  CirceJsonbMeta,
  Filterables,
  GeotrellisWktMeta
}

package object database
    extends CirceJsonbMeta
    with GeotrellisWktMeta
    with Filterables
