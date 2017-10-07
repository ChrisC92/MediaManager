
# Please enter the commit message for your changes. Lines starting
# with '#' will be ignored, and an empty message aborts the commit.
# On branch master
# Your branch is up-to-date with 'origin/master'.
#
# Changes to be committed:
#	modified:   out/production/MediaManager/ExtractingData/GetSeriesInfo.class
#	modified:   out/production/MediaManager/Metadata/Series.class
#	new file:   out/production/MediaManager/Metadata/SeriesList.class
#	modified:   src/ExtractingData/GetSeriesInfo.java
#	modified:   src/Metadata/Series.java
#	new file:   src/Metadata/SeriesList.java
#
# Untracked files:
#	.DS_Store
#	.idea/libraries/
#	.idea/misc.xml
#	.idea/modules.xml
#	.idea/uiDesigner.xml
#	.idea/workspace.xml
Get SeriesInfo class - returns an array of series with episodes both as an array of Files and Strings

CLass has two different ways of getting the info from a given file path, one stores the files however
this way is difficult to get to order correctly so have made another way of representing the episodes
as Strings. 

The Strings has the episodes ordered naturally however not been able to naturally order the SeriesList
objects yet
