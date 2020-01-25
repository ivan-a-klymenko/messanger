package ru.bk.klim9.chat.cache.friends

import androidx.room.*
import ru.bk.klim9.chat.data.friends.FriendsCache
import ru.bk.klim9.chat.domain.friends.FriendEntity

@Dao
interface FriendsDao : FriendsCache {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(friendEntity: FriendEntity): Long

    @Update
    fun update(friendEntity: FriendEntity)

    @Transaction
    override fun saveFriend(entity: FriendEntity) {
        if (insert(entity) == -1L) {
            update(entity)
        }
    }

    @Query("SELECT * from friends_table WHERE id = :key")
    override fun getFriend(key: Long): FriendEntity?

    @Query("SELECT * from friends_table WHERE is_request = 0")
    override fun getFriends(): List<FriendEntity>

    @Query("SELECT * from friends_table WHERE is_request = 1")
    override fun getFriendRequests(): List<FriendEntity>

    @Query("DELETE FROM friends_table WHERE id = :key")
    override fun removeFriendEntity(key: Long)
}