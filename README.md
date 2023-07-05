# taylor-swift-algorithm

The Taylor Swift Algorithm is a simplified simulation of a ticket purchasing process that suggests an alternative model to fairness away from a First-Come-First-Served concept

## Motivations
- Inspired by my own attempts to buy Taylor Swift tickets for the [Singapore Eras Tour](https://www.sportshub.com.sg/events/taylor-swift-the-eras-tour) via Ticketmaster, I found the Ticket Allocation system to be rather compelling and an interesting alternative to First-Come-First-Served. So I wanted to do a reverse-engineering over a simplified model


- The way Ticketmaster handled the sales is described below: 
1. Ticket sales open at a particular time, `t_sale`
2. If a user enters the Ticketmaster page at the current time, `t_curr` such that `t_curr < t_sale`, they are put into a Waiting Room. Users are only put into the Waiting Room if they arrive at `t_curr < t_sale`
3. When `t_curr > t_sale`, users from the Waiting Room can enter the payment service. Those who arrived at time `t_curr > t_sale` don't have to go to a Waiting Room but realistically they won't have a shot of buying tickets because of the SHEER number of people who came in early
4. To ensure some semblance of fair balloting, each user that enters the payment service are put into a holding queue where their queue numbers are randomised and this determines the order of who can make payments for tickets


- There are some interesting merits in such a protocol - one of which is that it somewhat eliminates "Fastest Fingers First", especially when the difference in entry time between users can go as small as nanoseconds, and those who enter at batches similar times (within an acceptable range) have a fair shot at getting the ticket WITHIN their timing batch


- Realistically for the Eras Tour, for the set of people in the Waiting Room `W` BEFORE sale time `t`, and the set of tickets `T`, it was clear to me that `|W| > |T|` so in that case the algorithm is straightforward - shuffle the order of everyone from the Waiting Room as they are transitioned into the holding queue.


- However, what was interesting to me was this scenario: If instead `|W| < |T|` (i.e. less people in the Waiting Room than tickets available, remember the Waiting Room is only for people who arrive at `t_curr < t`) BUT there are more users coming in to buy and they arrive at `t_curr > t_sale`, how would a Ticket Allocation algorithm handle their queue positions? If we're randomising queue numbers within people of the same arrival time batch, surely we must be careful not to displace those who arrived at an earlier time batch?

## Assumptions

To simplify the model, we make these following assumptions:
1. For the set of Users, `U` attempting to purchase from a set of tickets `T`, we assume `|U| > |T|`. In other words, there are more users trying to buy tickets than actual tickets available


1. We define a function `f: U -> T` such that for each user `u ∈ U`, `f(u) = t`, where `t ∈ T`. The function `f` represents the process of a user buying a ticket. In other words, we want to ensure that each user can only buy one ticket, meaning the function `f` is well-defined and each user is mapped to a unique ticket. It is worth revisiting this assumption and explore how the algorithm changes if users can buy several tickets


1. When a user attempts to pay for the ticket, they are successful. Again, this is worth revisiting in a future iteration of the project.

1. Time is defined as a discrete variable

## Provable Properties

- Let `U` denote the set of all users.
- Let `T` denote the set of all tickets.
- Let `t(u)` denote the time when user u arrives.
- Let `p(u)` denote the ticket purchase attempt by user u.
- Let `q(u)` denote the queue position of user u.

1. **Progress**: For `∀t ∈ T`, if `|U| ≥ |T|`, `∃u ∈ U` such that the ticket purchase `p(u)` for the ticket `t` is successful. In simpler terms, if the number of users is greater than or equal to the number of tickets, every ticket will eventually be sold.

  
1. **Mutual Exclusion**: For `∀t ∈ T` and for every pair of users `{u, v} ∈ U`, if user `u` is in the process of a ticket purchase `p(u)` for the ticket `t`, then no other user `v` can initiate a ticket purchase `p(v)` for the ticket `t`. In simpler terms, only one user can attempt to purchase a specific ticket at a given time.

1. **No Starvation**: For `∀u ∈ U` and any ticket `t ∈ T`, if user `u` is willing to purchase a ticket and tickets are available (`|U| > |T|`), there exists a time `t'` such that for all `t'' > t'`, user `u` will have been given an opportunity to purchase a ticket. In simpler terms, if tickets are available and a user wants to purchase a ticket, then that user will eventually be given an opportunity to make a purchase.

1. **Fairness**: For any two users `{u, v} ∈ U`, where `t(u) = t(v)`, the absolute difference between `q(u)` and `q(v)` should be within an acceptable range `r`. Formally, `t(u) = t(v) → |q(u) - q(v)| <= r`. This means if two users arrive at the same time, their positions in the queue shouldn't be too far apart, where the definition of "too far" is determined by the acceptable range `r`.

## Project Structure

The project is structured into several Java classes, each encapsulating a part of the system:

- `User.java`: Represents a user in the system
- `Ticket.java`: Represents a ticket that a user can purchase.
- `WaitingRoomService.java`: Manages the waiting room where users are stored before they can purchase tickets.
- `QueueManagerService.java`: Manages the queue of users waiting to purchase tickets.
- `QueueService.java`: Processes the queue of users and attempts to let each user purchase a ticket.
- `TicketingSystem.java`: The main class that combines all services and runs the simulation.
- `App.java`: The entry point of the application.

## Usage

### Prerequisites

Have Java 8 or later installed on your machine.

### Installation

1. Clone the repo
   ```sh
   git clone https://github.com/nicholas-gcc/taylor-swift-algorithm.git
   ```
2. Compile the project
   ```sh
   javac -d bin src/main/ticketing/system/*.java src/main/ticketing/system/**/*.java
   ```
   This command compiles all the Java files in the specified directories and saves the compiled bytecode files in the bin directory.


3. Run the entry point of the application
   ```sh
   java -cp bin ticketing.system.App
   ```

## Contributing

This project is open for improvements and bugfixes. Feel free to submit a pull request or open an issue.


