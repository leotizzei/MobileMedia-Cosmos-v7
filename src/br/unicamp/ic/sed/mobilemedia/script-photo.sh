 #!/bin/bash
        for i in $( ls ); do
	    find $i -name "*.java" -or -name "*.aj" | xargs more | grep -E includePhoto > $i.txt
        done

