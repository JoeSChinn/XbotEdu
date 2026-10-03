package competition.subsystems.drive.commands;

import javax.inject.Inject;

import xbot.common.command.BaseCommand;
import competition.subsystems.drive.DriveSubsystem;
import competition.subsystems.pose.PoseSubsystem;
import xbot.common.math.XYPair;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.Property;
import xbot.common.properties.PropertyFactory;

public class DriveToPositionCommand extends BaseCommand {

    DriveSubsystem drive;
    PoseSubsystem pose;
    double setPosition;
    double currentPosition;
    double power;
    double error;
    DoubleProperty pProperty;
    double prevError;
    double velocity;

    @Inject
    public DriveToPositionCommand(DriveSubsystem driveSubsystem, PoseSubsystem pose, PropertyFactory propertyFactory) {
        this.drive = driveSubsystem;
        this.pose = pose;
        propertyFactory.setPrefix(this);
        pProperty = propertyFactory.createPersistentProperty("p",1);
    }

    public void setTargetPosition(double position) {
        // This method will be called by the test, and will give you a goal distance.
        // You'll need to remember this target position and use it in your calculations.
        setPosition = position;
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        // Here you'll need to figure out a technique that:
        // - Gets the robot to move to the target position
        // - Hint: use pose.getPosition() to find out where you are
        // - Gets the robot stop (or at least be moving really really slowly) at the
        // target position

        // How you do this is up to you. If you get stuck, ask a mentor or student for
        // some hints!
        currentPosition = pose.getPosition();
        error = setPosition - currentPosition;
        velocity = prevError - error;
        power = 0.18 * error - 0.03/error;
        drive.tankDrive(power,power);
        prevError = error;
    }

    @Override
    public boolean isFinished() {
        return (error <= 0.1 && velocity <= 0.0025);
        // Modify this to return true once you have met your goal,
        // and you're moving fairly slowly (ideally stopped)
    }

}
