-- Part 1: Test it with SQL
-- SQL TASK: At this point, you will have one table, job.
-- In queries.sql under “Part 1”, list the columns and their data types
-- in the table as a SQL comment.
SELECT id, employer, name, skills  from job;
-- id = int, employer = varchar, name = varchar, skills =  varchar


-- Part 2: Test it with SQL
--  write a query to list the names of the employers in St. Louis City.
--  Do NOT specify an ordering for the query results.

SELECT name from employer where location = "St. Louis City";




-- Part 3: Test it with SQL
-- write the SQL statement to remove the job table.
DROP TABLE job;

-- Part 4: Test it with SQL


SELECT * FROM skill
INNER JOIN job_skills ON skill.id = job_skills.skills_id
WHERE job_skills.jobs_id IS NOT NULL
ORDER BY name ASC;


-- write a query to return the names of all skills
--  that are attached to jobs in alphabetical order. If a skill does not have a job listed,
--   it should not be included in the results of this query.


