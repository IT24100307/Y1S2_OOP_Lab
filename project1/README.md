# Vehicle Rental Payment System

This project implements an online payment system for a vehicle rental service. It provides a robust backend API for processing payments and an interactive frontend interface.

## Features

- Multiple payment methods (Cash or Card)
- Card payment options (Visa or Mastercard)
- Secure payment processing using Stripe
- Complete payment flow with client-side and server-side validation
- Multi-step payment process with confirmation screen
- Transaction history and receipts
- Responsive design for mobile and desktop

## Payment Flow

1. **Payment Method Selection**

   - Choose between Card Payment or Cash Payment

2. **Card Details Entry** (for Card Payment)

   - Select card type (Visa or Mastercard)
   - Enter card owner name
   - Enter 10-digit card number
   - Enter expiry date
   - Enter 4-digit CVC code

3. **Payment Confirmation** (for Card Payment)

   - Review payment details
   - Option to edit details or confirm payment

4. **Status**
   - For Card Payments: Success confirmation with receipt
   - For Cash Payments: Pending status with booking details
   - Option to return to homepage

## Technology Stack

- **Backend**: Spring Boot 3.x
- **Payment Gateway**: Stripe
- **Database**: H2 (embedded database)
- **Frontend**: HTML, CSS, JavaScript

## Backend API Endpoints

### Payment API

- `POST /api/payments/create-intent`: Create a payment intent for processing

  - Parameters: `bookingId` (Long), `paymentMethodId` (String)
  - Returns: Payment intent details with client secret

- `POST /api/payments/confirm`: Confirm a payment after client-side confirmation

  - Parameters: `paymentIntentId` (String)
  - Returns: Payment confirmation status

- `POST /api/payments/webhook`: Stripe webhook endpoint for receiving events
  - Used for asynchronous payment event handling

### Booking API

- `POST /api/bookings`: Create a new rental booking

  - Body: Booking details object
  - Returns: Created booking with ID and booking number

- `GET /api/bookings/{id}`: Get booking details by ID

  - Returns: Booking object with all details

- `GET /api/bookings/user/{userId}`: Get all bookings for a specific user
  - Returns: List of bookings

## Getting Started

### Prerequisites

- Java 17 or later
- Maven 3.x
- Stripe account (for API keys)

### Configuration

Update the `application.properties` file with your Stripe API keys:

```properties
stripe.api.key=sk_test_your_stripe_test_key
stripe.public.key=pk_test_your_stripe_public_key
```

### Running the Application

```bash
mvn spring-boot:run
```

The application will start on http://localhost:8080

### Accessing the Payment Interface

Open http://localhost:8080/payment in your browser to access the payment interface.

## Security Considerations

- Never expose your Stripe secret key on the client side
- Always validate payment amounts on the server side
- Implement proper authentication and authorization
- Use HTTPS in production
- Handle webhook events securely to prevent replay attacks

## Validation Rules

- Card Number: Must be exactly 10 digits
- CVC Code: Must be exactly 4 digits
- Card Owner Name: Required
- Card Type: Must select either Visa or Mastercard

## Testing

For testing the payment system with the sample interface:

1. Select payment method (Card or Cash)
2. If Card selected:
   - Choose a card type (Visa or Mastercard)
   - Enter card details (use any valid 10-digit number)
   - Enter a 4-digit CVC
   - Review the confirmation screen
   - Confirm payment to see the success screen
3. If Cash selected:
   - System will immediately show the pending status screen
   - Booking is created but marked as pending until cash payment at pickup

## Frontend Integration

### Sample Implementation

A sample frontend implementation is provided in the `src/main/resources/static/sample-frontend` directory. This includes:

- `PaymentForm.js`: React component for payment processing
- `PaymentForm.css`: Styles for the payment form
- `payment.html`: Sample HTML implementation

### Integration Steps

1. Include Stripe.js in your frontend application:

   ```html
   <script src="https://js.stripe.com/v3/"></script>
   ```

2. Initialize Stripe with your publishable key:

   ```javascript
   const stripe = Stripe("pk_test_your_stripe_public_key");
   ```

3. Create and mount the Stripe Card Element:

   ```javascript
   const elements = stripe.elements();
   const cardElement = elements.create("card");
   cardElement.mount("#card-element");
   ```

4. Handle form submission:

   ```javascript
   // Create a payment method
   const { paymentMethod } = await stripe.createPaymentMethod({
     type: "card",
     card: cardElement,
   });

   // Create a payment intent on the server
   const response = await fetch(
     "/api/payments/create-intent?bookingId=123&paymentMethodId=" +
       paymentMethod.id
   );
   const data = await response.json();

   // Handle the response - may require additional authentication
   if (data.requiresAction) {
     await stripe.handleCardAction(data.clientSecret);
   }

   // Confirm the payment
   const confirmResponse = await fetch(
     "/api/payments/confirm?paymentIntentId=" + data.paymentIntentId
   );
   const confirmData = await confirmResponse.json();
   ```

## Getting Started

### Prerequisites

- Java 17 or later
- Maven 3.x
- Stripe account (for API keys)

### Configuration

Update the `application.properties` file with your Stripe API keys:

```properties
stripe.api.key=sk_test_your_stripe_test_key
stripe.public.key=pk_test_your_stripe_public_key
```

### Running the Application

```bash
mvn spring-boot:run
```

The application will start on http://localhost:8080

### Accessing the Sample Payment Page

Open http://localhost:8080/sample-frontend/payment.html in your browser to see the sample payment page.

## Security Considerations

- Never expose your Stripe secret key on the client side
- Always validate payment amounts on the server side
- Implement proper authentication and authorization
- Use HTTPS in production
- Handle webhook events securely to prevent replay attacks

## Testing

You can use Stripe's test cards for testing the payment system:

- Success: 4242 4242 4242 4242
- Requires Authentication: 4000 0025 0000 3155
- Declined: 4000 0000 0000 0002

## Production Deployment

Before deploying to production:

1. Replace test API keys with production keys
2. Connect to a proper database (MySQL, PostgreSQL, etc.)
3. Configure proper logging and monitoring
4. Set up comprehensive error handling
5. Implement additional security measures
