<h2>Setup</h2>
This is a pretty typical Java + Tomcat + MySQL setup. While the code does use a few libraries (javax.servlet, Apache.math, and fasterxml), I've avoided most of the heavier frameworks (Maven, Hibernate) that might be used with similar projects in order to keep this light. <p>
While no Hibernate is involved, some caching is performed in the Daos to reduce the number of SQL calls. 
<h2>About the data</h2>
The data was retrieved from the Jeopardy! Archive (http://j-archive.com/). It is current as of early May 2016. 