// Query 6
// Find the average friend count per user.
// Return a decimal value as the average user friend count of all users in the users collection.

function find_average_friendcount(dbname) {
    db = db.getSiblingDB(dbname);

    // TODO: calculate the average friend count
    let totalFriends = 0
    let num_users = 0;
    db.users.find().forEach((user) => {
        ++num_users
        totalFriends += user.friends.length
    })
    return totalFriends/num_users;
}
