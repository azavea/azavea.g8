package $package$.database

import $package$.database.util.Dao
import $package$.datamodel.User

import doobie._
import doobie.implicits._
import doobie.postgres.implicits._
import doobie.util.fragment.Fragment

import java.util.UUID

object UserDao extends Dao[User] {
  val tableName: String = "users"

  /** An abstract select statement to be used for constructing queries */
  def selectF: Fragment = fr"SELECT id, email FROM" ++ tableF

  def create(user: User.Create): ConnectionIO[User] = {
    (fr"INSERT INTO" ++ tableF ++ fr"""
      (id, email)
    VALUES
      (uuid_generate_v4(), \${user.email})
    """).update.withUniqueGeneratedKeys[User]("id", "email")
  }

  def update(id: UUID, user: User): ConnectionIO[Int] = {
    val updateQuery = fr"UPDATE" ++ tableF ++ fr"SET email = \${user.id} WHERE id = \${id}"
    updateQuery.update.run
  }

}
