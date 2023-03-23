VAR = 10;

Select first_name, last_name, gender, year_of_birth, month_of_birth, day_of_birth
from project3.Public_Users
Where user_id = 10;

Select city_name, state_name, country_name
from project3.Public_Cities C
Join project3.Public_User_Current_Cities CC
ON C.city_id = CC.current_city_id
WHERE CC.user_id = 10;

Select city_name, state_name, country_name
from project3.Public_Cities C
Join project3.Public_User_Hometown_Cities HC
ON C.city_id = HC.hometown_city_id
WHERE HC.user_id = 10;

Select user2_id from project3.Public_Friends
where user1_id = 10 and user2_id > user1_id;

select distinct user_id from project3.Public_Users;
