/* AUTO-GENERATED FILE.  DO NOT MODIFY.
 *
 * This class was automatically generated by the
 * java mavlink generator tool. It should not be modified by hand.
 */

package syk.drone.mavlink.enums;

/** 
* Direction of VTOL transition
*/
public class VTOL_TRANSITION_HEADING {
   public static final int VTOL_TRANSITION_HEADING_VEHICLE_DEFAULT = 0; /* Respect the heading configuration of the vehicle. | */
   public static final int VTOL_TRANSITION_HEADING_NEXT_WAYPOINT = 1; /* Use the heading pointing towards the next waypoint. | */
   public static final int VTOL_TRANSITION_HEADING_TAKEOFF = 2; /* Use the heading on takeoff (while sitting on the ground). | */
   public static final int VTOL_TRANSITION_HEADING_SPECIFIED = 3; /* Use the specified heading in parameter 4. | */
   public static final int VTOL_TRANSITION_HEADING_ANY = 4; /* Use the current heading when reaching takeoff altitude (potentially facing the wind when weather-vaning is active). | */
   public static final int VTOL_TRANSITION_HEADING_ENUM_END = 5; /*  | */
}
            