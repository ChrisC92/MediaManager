
# Please enter the commit message for your changes. Lines starting
# with '#' will be ignored, and an empty message aborts the commit.
# On branch master
# Your branch is up-to-date with 'origin/master'.
#
# Changes to be committed:
#	modified:   out/production/MediaManager/storageAndExtraction/GetSeriesInfo.class
#	modified:   out/production/MediaManager/metadata/Series.class
#	new file:   out/production/MediaManager/metadata/SeriesList.class
#	modified:   src/storageAndExtraction/GetSeriesInfo.java
#	modified:   src/metadata/Series.java
#	new file:   src/metadata/SeriesList.java
#
# Untracked files:
#	.DS_Store
#	.idea/libraries/
#	.idea/misc.saveandload
#	.idea/modules.saveandload
#	.idea/uiDesigner.saveandload
#	.idea/workspace.saveandload
Get SeriesInfo class - returns an array of series with episodes both as an array of Files and Strings

CLass has two different ways of getting the info from a given file path, one stores the files however
this way is difficult to get to order correctly so have made another way of representing the episodes
as Strings. 

The Strings has the episodes ordered naturally however not been able to naturally order the SeriesList
objects yet
