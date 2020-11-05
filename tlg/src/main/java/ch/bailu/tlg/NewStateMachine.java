package ch.bailu.tlg;

public class NewStateMachine {
	public abstract class State {
		public abstract State inputStart();
		public abstract State inputPause();
		public abstract State inputLocked();
	}
	
	
	
	
	public class Init extends State {

		@Override
		public State inputStart() {
			return this;
		}

		@Override
		public State inputPause() {
			return this;
		}

		@Override
		public State inputLocked() {
			return this;
		}
	}
	
	
	public class Running extends State {

		@Override
		public State inputStart() {
			return this;
		}

		@Override
		public State inputPause() {
			return new Paused();
		}

		@Override
		public State inputLocked() {
			return new Locked();
		}
		
	}
	
	
	public class Paused extends State {

		@Override
		public State inputStart() {
			return this;
		}

		@Override
		public State inputPause() {
			return new Running();
		}

		@Override
		public State inputLocked() {
			return new Locked();
		}
		
		
	}
	
	public class Locked extends State {

		@Override
		public State inputStart() {
			return new Running();
		}

		@Override
		public State inputPause() {
			return this;
		}

		@Override
		public State inputLocked() {
			return this;
		}
	}
}


