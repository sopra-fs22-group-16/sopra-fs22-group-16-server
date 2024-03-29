package ch.uzh.ifi.hase.soprafs22.game.units.commands;

import ch.uzh.ifi.hase.soprafs22.game.Position;

import java.util.Objects;

public class MoveCommand {
    Position start;
    Position destination;

    public MoveCommand(Position start, Position destination) {
        this.start = start;
        this.destination = destination;
    }

    public Position getStart() {
        return start;
    }

    public Position getDestination() {
        return destination;
    }

    @Override
    public boolean equals(Object o) {
        // If the object is compared with itself then return true
        if (o == this) {
            return true;
        }

        /* Check if o is an instance of Position or not
          "null instanceof [type]" also returns false */
        if (!(o instanceof MoveCommand)) {
            return false;
        }

        // typecast o to Complex so that we can compare data members
        MoveCommand c = (MoveCommand) o;

        // Compare the data members and return accordingly
        return start.equals(c.start) && destination.equals(c.destination);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, destination);
    }
}
