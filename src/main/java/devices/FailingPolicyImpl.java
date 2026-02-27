package devices;

import java.util.Iterator;
import java.util.stream.Stream;

public class FailingPolicyImpl implements FailingPolicy {
    private final Iterator<Boolean> iterator;
    private boolean failed = false;

    public FailingPolicyImpl(Stream<Boolean> stream) {
        this.iterator = stream.iterator();
    }

    public FailingPolicyImpl() {
        this.iterator = Stream.generate(Math::random)
                .map(r -> r < 0.5)
                .iterator();
    }

    @Override
    public boolean attemptOn() {
        if (!iterator.hasNext()) {
            throw new IllegalStateException("FailingPolicy iterator exhausted");
        }
        this.failed = this.failed || iterator.next();
        return !this.failed;
    }

    @Override
    public void reset() {
        this.failed = false;
    }

    @Override
    public String policyName() {
        return "random";
    }
}
