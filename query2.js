// Query 2
// Unwind friends and create a collection called 'flat_users' where each document has the following schema:
// {
//   user_id:xxx
//   friends:xxx
// }
// Return nothing.

function unwind_friends(dbname) {
    db = db.getSiblingDB(dbname);

    // TODO: unwind friends
    db.createCollection("flat_users")
    friendsData = db.users.aggregate( [ { $unwind : "friends" } ] )
    friendsData.forEach((entry) => {
        print(entry)
        db.flat_users.insert({"user_id":entry.user_id, "friends":entry.friends})

    })

    return;
}
