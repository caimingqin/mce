package cmq.com;

import org.springframework.beans.factory.annotation.Autowired;

import com.mce.command.AbstractEventCommand;
import com.mce.command.DomainEventGather;
import com.mce.core.inject.Bean;

@Bean(name="test", type = "Command")
public class TestCommand extends AbstractEventCommand{

	@Autowired
	private String name;
	@Override
	public Object execute(DomainEventGather paramDomainEventGather) {
		// TODO Auto-generated method stub
		return null;
	}

}
