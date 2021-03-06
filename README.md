CS5320-Fall-HW3
===============

Database HomeWork 3 @ CornellTech

Author: Lizhang Li(ll725@cornell.edu)
        Zhigang Wang(zw344@cornell.edu)

The purpose of this assignment is to build a very simple database management system to store GPS trajectories. The system should be built in Java.

The trajectory information is stored at the directory "Data/"
Each trajectory set has a special folder named after its trajectory set name.
Each trajectory is kept inside the folder "Data/\<traj_set_name\>/Trajectory/\<traj_id\>" as an independent file.
Each line inside the trajectory file stands for a measure.

Whenever an insertion operation is being executed, a new file is created inside the corresponding trajectory set folder.
Whenever a deletion operation is being executed, the corresponding file is deleted.

General Usage:
    CREATE \<tname\>;
    INSERT INTO \<tname\> VALUES \<sequence\>;
    DELETE FROM \<tname\> TRAJECTORY \<id\>;
    RETRIEVE FROM \<tname\> TRAJECTORY \<id\>;
    RETRIEVE FROM \<tname\> COUNT OF \<id\>;
    RETRIEVE FROM \<tname\> WHERE \<field\>\<op\>\<val\>;
    EXIT;

For advanced retreival, the field options can be "lati"(short for latitude),"long" or "alti".
    the \<op\> can be "\>=","\<=","=","\<","\>"
    the \<val\> should be a double or integer number.
    notice that there should be no space between \<filed\>,\<op\> and \<val\>
Example:
    RETRIEVE FROM traj1 WHERE lati\>39.987;

