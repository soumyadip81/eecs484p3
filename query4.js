// Query 4
// Find user pairs (A,B) that meet the following constraints:
// i) user A is male and user B is female
// ii) their Year_Of_Birth difference is less than year_diff
// iii) user A and B are not friends
// iv) user A and B are from the same hometown city
// The following is the schema for output pairs:
// [
//      [user_id1, user_id2],
//      [user_id1, user_id3],
//      [user_id4, user_id2],
//      ...
//  ]
// user_id is the field from the users collection. Do not use the _id field in users.
// Return an array of arrays.

function suggest_friends(year_diff, dbname) {
    db = db.getSiblingDB(dbname);

    let pairs = [];
    // TODO: implement suggest friends
    male_users = db.users.find({gender: "male"}).toArray()
    female_users = db.users.find({gender: "female"}).toArray()
    for(let i = 0; i < male_users.length; ++i){
        for(let j = 0; j < female_users.length; ++j){
            if(Math.abs(male_users[i].YOB - female_users[j].YOB) < year_diff 
            && male_users[i].friends.includes(female_users[j].user_id) == false
            && female_users[j].friends.includes(male_users[i].user_id) == false
            && male_users[i].hometown.city === female_users[j].hometown.city){
                innerArr = [male_users[i].user_id, female_users[j].user_id]
                pairs.push(innerArr)
            }

        }
    }



    return pairs;
}
