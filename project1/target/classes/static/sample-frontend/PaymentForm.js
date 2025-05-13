// This file provides a sample implementation for integrating with the payment system
// Place this in your frontend React or similar application

import React, { useState } from 'react';
import { loadStripe } from '@stripe/stripe-js';
import { CardElement, Elements, useStripe, useElements } from '@stripe/react-stripe-js';
import axios from 'axios';
import './PaymentForm.css';

// Replace with your Stripe publishable key
const stripePromise = loadStripe('pk_test_your_stripe_public_key');

const API_BASE_URL = 'http://localhost:8080/api';

const CheckoutForm = ({ bookingId, amount, onPaymentSuccess, onPaymentError }) => {
  const stripe = useStripe();
  const elements = useElements();
  const [loading, setLoading] = useState(false);
  const [errorMessage, setErrorMessage] = useState('');

  const handleSubmit = async (event) => {
    event.preventDefault();
    
    if (!stripe || !elements) {
      // Stripe.js has not loaded yet
      return;
    }
    
    setLoading(true);
    setErrorMessage('');
    
    try {
      // Create a PaymentMethod
      const { error, paymentMethod } = await stripe.createPaymentMethod({
        type: 'card',
        card: elements.getElement(CardElement),
      });
      
      if (error) {
        throw new Error(error.message);
      }
      
      // Create a PaymentIntent on the server
      const { data: intentResponse } = await axios.post(
        `${API_BASE_URL}/payments/create-intent`,
        null,
        { params: { bookingId, paymentMethodId: paymentMethod.id } }
      );
      
      // Handle next steps based on the response
      if (intentResponse.requiresAction) {
        // Use Stripe.js to handle the required authentication
        const { error: authError } = await stripe.handleCardAction(intentResponse.clientSecret);
        
        if (authError) {
          throw new Error(authError.message);
        }
        
        // After authentication complete, confirm the payment
        await confirmPayment(intentResponse.paymentIntentId);
      } else {
        // No authentication required, just confirm the payment
        await confirmPayment(intentResponse.paymentIntentId);
      }
    } catch (err) {
      setErrorMessage(err.message || 'An error occurred during payment processing.');
      onPaymentError(err.message);
    } finally {
      setLoading(false);
    }
  };
  
  const confirmPayment = async (paymentIntentId) => {
    const { data: confirmResponse } = await axios.post(
      `${API_BASE_URL}/payments/confirm`,
      null,
      { params: { paymentIntentId } }
    );
    
    if (confirmResponse.success) {
      onPaymentSuccess(confirmResponse);
    } else if (confirmResponse.requiresAction) {
      // Handle additional authentication if needed
      const { error } = await stripe.handleCardAction(confirmResponse.clientSecret);
      
      if (error) {
        throw new Error(error.message);
      }
      
      // Try confirming again after authentication
      await confirmPayment(paymentIntentId);
    } else {
      throw new Error(confirmResponse.error || 'Payment failed');
    }
  };
  
  return (
    <form onSubmit={handleSubmit} className="payment-form">
      <div className="form-row">
        <label htmlFor="card-element">Credit or debit card</label>
        <CardElement
          id="card-element"
          options={{
            style: {
              base: {
                fontSize: '16px',
                color: '#424770',
                '::placeholder': {
                  color: '#aab7c4',
                },
              },
              invalid: {
                color: '#9e2146',
              },
            },
          }}
        />
      </div>
      
      {errorMessage && <div className="error-message">{errorMessage}</div>}
      
      <button type="submit" disabled={!stripe || loading} className="payment-button">
        {loading ? 'Processing...' : `Pay $${(amount).toFixed(2)}`}
      </button>
    </form>
  );
};

const PaymentForm = ({ bookingId, amount, onPaymentSuccess, onPaymentError }) => {
  return (
    <div className="payment-container">
      <h2>Complete Your Rental Payment</h2>
      <div className="amount-display">
        <span>Total Amount:</span>
        <span className="amount">${amount.toFixed(2)}</span>
      </div>
      <Elements stripe={stripePromise}>
        <CheckoutForm 
          bookingId={bookingId} 
          amount={amount} 
          onPaymentSuccess={onPaymentSuccess} 
          onPaymentError={onPaymentError} 
        />
      </Elements>
    </div>
  );
};

export default PaymentForm;
